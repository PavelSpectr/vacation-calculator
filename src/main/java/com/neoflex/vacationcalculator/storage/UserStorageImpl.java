package com.neoflex.vacationcalculator.storage;

import com.neoflex.vacationcalculator.model.Status;
import com.neoflex.vacationcalculator.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class UserStorageImpl implements UserStorage {
    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        log.debug("Создание пользователя...");
        userValidation(user);
        user.setId(UUID.randomUUID());
        users.put(user.getId(), user);
        log.debug("Сотрудник успешно добавлен: {} {}", user.getLastName(), user.getName());

        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new ValidationException("Такого работника не существует.");
        }
        userValidation(user);
        users.put(user.getId(), user);
        log.debug("Данные сотрудника {} {} успешно изменены.", user.getLastName(), user.getName());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new ValidationException("Такого сотрудника не существует.");
        }
        users.remove(user.getId(), user);
        log.debug("Сотрудник успешно удален: {} {}", user.getLastName(), user.getName());
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Текущее количество сотрудников: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> getUserByName(String name) {
        if (name == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        List<User> foundUsersByName = new ArrayList<>();
        log.debug("Пытаюсь найти сотрудника с именем {}.", name);
        for (User user : users.values()) {
            if (user.getName().equals(name)) {
                log.debug("Успех! Сотрудник с именем {} найден", name);
                foundUsersByName.add(user);
            } else {
                log.debug("Отказ! Сотрудник с именем {} не найден", name);
                return null;
            }
        }
        return foundUsersByName;
    }

    @Override
    public List<User> getUserByLastName(String lastName) {
        if (lastName == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        List<User> foundUsersByLastName = new ArrayList<>();
        log.debug("Пытаюсь найти сотрудников с фамилией {}.", lastName);
        for (User user : users.values()) {
            if (user.getLastName().equals(lastName)) {
                log.debug("Успех! Сотрудник с фамилией {} найден", lastName);
                foundUsersByLastName.add(user);
            } else {
                log.debug("Отказ! Сотрудник с фамилией {} не найден", lastName);
                return null;
            }
        }
        return foundUsersByLastName;
    }

    @Override
    public List<User> getUserByPatronymic(String patronymic) {
        if (patronymic == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        List<User> foundUsersByPatronymic = new ArrayList<>();
        log.debug("Пытаюсь найти пользователя с отчеством {}.", patronymic);
        for (User user : users.values()) {
            if (user.getPatronymic().equals(patronymic)) {
                log.debug("Успех! Сотрудник с отчеством {} найден", patronymic);
                foundUsersByPatronymic.add(user);
            } else {
                log.debug("Отказ! Сотрудник с отчеством {} не найден", patronymic);
                return null;
            }
        }
        return foundUsersByPatronymic;
    }

    @Override
    public List<User> getUserByBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw  new ValidationException("Ошибка: Неверный формат даты, либо попытка поиска пустого значения. Введите дату в формате: ДД.ММ.ГГГГ!");
        }
        List<User> foundUsersByBirthday = new ArrayList<>();
        log.debug("Пытаюсь найти сотрудника по дате рождения {}.", birthday);
        for (User user : users.values()) {
            if (user.getBirthday().equals(birthday)) {
                log.debug("Успех! Сотрудник с датой рождения {} найден.", birthday);
                foundUsersByBirthday.add(user);
            } else {
                log.debug("Отказ! Сотрудник с датой рождения {} не найден.", birthday);
                return null;
            }
        }
        return foundUsersByBirthday;
    }


    @Override
    public List<User> getUserByFullNameAndBirthday(User user, String name, String lastName, String patronymic, LocalDate birthday) {
        Set<User> foundByFullNameAndBirthday = new HashSet<>();
        log.debug("Поиск по дате рождения...");
        List<User> byBirthday = getUserByBirthday(birthday);
        log.debug("Поиск по фамилии...");
        List<User> byLastName = getUserByLastName(lastName);
        log.debug("Поиск по имени...");
        List<User> byName = getUserByName(name);
        log.debug("Поиск по отчеству...");
        List<User> byPatronymic = getUserByPatronymic(patronymic);
        log.debug("Формирую ответ...");
        if (byBirthday != null) {
            foundByFullNameAndBirthday.addAll(byBirthday);
        }
        if (byLastName != null) {
            foundByFullNameAndBirthday.addAll(byLastName);
        }
        if (byName != null) {
            foundByFullNameAndBirthday.addAll(byName);
        }
        if (byPatronymic != null) {
            foundByFullNameAndBirthday.addAll(byPatronymic);
        }
        log.debug("Найдено сотрудников: {}", foundByFullNameAndBirthday.size());
        if (foundByFullNameAndBirthday.size() > 1) {
            log.warn("Внимание! Возможно наличие дубликатов! Рекомендуется удалить дубликаты сотрудника!");
        } else if (foundByFullNameAndBirthday.isEmpty()) {
            log.error("Не найдено ни одного сотрудника! Проверьте корректность данных запроса!");
        } else {
            log.debug("Сотрудник найден!");
        }
        return foundByFullNameAndBirthday.stream().toList();
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        log.debug("Пытаюсь найти сотрудника по E-mail: {}.", email);
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                log.debug("Успех! Сотрудник с E-mail {} найден", email);
                return user;
            }
        }
        log.debug("Отказ! Сотрудник E-mail: {} не найден", email);
        return null;
    }

    private void userValidation(User user) {
        if (user.getBirthday().isAfter(LocalDate.now().minusYears(18))) {
            throw new ValidationException("Сотрудник не может быть младше 18 лет.");
        }
        if (user.getLastVacationDate().isBefore(user.getHired())) {
            throw new ValidationException("Сотрудник не мог находиться в отпуске до того, как был нанят.");
        }
        if (user.getHired() == null) {
            user.setHired(LocalDate.now());
        }
        if (user.getLastVacationDate() == null) {
            user.setLastVacationDate(user.getHired());
        }
        if (user.getFired() == null || user.getFired().isAfter(LocalDate.now())) {
            user.setStatus(Status.WORKING);
        } else {
            user.setStatus(Status.FIRED);
        }
    }
}
