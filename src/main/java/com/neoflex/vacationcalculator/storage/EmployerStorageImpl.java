package com.neoflex.vacationcalculator.storage;

import com.neoflex.vacationcalculator.model.Status;
import com.neoflex.vacationcalculator.model.Employer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class EmployerStorageImpl implements EmployerStorage {
    private final Map<String, Employer> employers = new HashMap<>();

    @Override
    public Employer createEmployer(Employer employer) {
        log.debug("Создание пользователя...");
        userValidation(employer);
        employer.setId(UUID.randomUUID());
        employers.put(employer.getEmail(), employer);
        log.debug("Сотрудник успешно добавлен: {} {}", employer.getLastName(), employer.getName());

        return employer;
    }

    @Override
    public Employer updateEmployer(Employer employer) {
        if (employer.getEmail() == null || !employers.containsKey(employer.getEmail())) {
            throw new ValidationException("Такого работника не существует.");
        }
        userValidation(employer);
        employers.put(employer.getEmail(), employer);
        log.debug("Данные сотрудника {} {} успешно изменены.", employer.getLastName(), employer.getName());
        return employer;
    }

    @Override
    public void deleteEmployer(Employer employer) {
        if (employer.getEmail() == null || !employers.containsKey(employer.getEmail())) {
            throw new ValidationException("Такого сотрудника не существует.");
        }
        employers.remove(employer.getEmail(), employer);
        log.debug("Сотрудник успешно удален: {} {}", employer.getLastName(), employer.getName());
    }

    @Override
    public List<Employer> getAllEmployers() {
        log.debug("Текущее количество сотрудников: {}", employers.size());
        return new ArrayList<>(employers.values());
    }

    @Override
    public Employer getEmployerByEmail(String email) {
        if (email == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        log.debug("Пытаюсь найти сотрудника по E-mail: {}.", email);
        for (Employer employer : employers.values()) {
            if (employer.getEmail().equals(email)) {
                log.debug("Успех! Сотрудник с E-mail {} найден", email);
                return employer;
            }
        }
        log.debug("Отказ! Сотрудник E-mail: {} не найден", email);
        return null;
    }

    @Override
    public List<Employer> getEmployerByFullNameAndBirthday(Employer employer) {
        Set<Employer> foundByFullNameAndBirthday = new HashSet<>();
        log.debug("Поиск по дате рождения...");
        List<Employer> byBirthday = getUserByBirthday(employer.getBirthday());
        log.debug("Поиск по фамилии...");
        List<Employer> byLastName = getUserByLastName(employer.getLastName());
        log.debug("Поиск по имени...");
        List<Employer> byName = getUserByName(employer.getName());
        log.debug("Поиск по отчеству...");
        List<Employer> byPatronymic = getUserByPatronymic(employer.getPatronymic());
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
        if (!foundByFullNameAndBirthday.isEmpty() && foundByFullNameAndBirthday.size() > 1) {
            log.warn("Внимание! Возможно наличие дубликатов! Рекомендуется удалить дубликаты сотрудника!");
        } else if (foundByFullNameAndBirthday.isEmpty()) {
            log.error("Не найдено ни одного сотрудника! Проверьте корректность данных запроса!");
        } else {
            log.debug("Сотрудник найден!");
        }
        return foundByFullNameAndBirthday.stream().toList();
    }

    public List<Employer> getUserByName(String name) {
        if (name == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        List<Employer> foundUsersByName = new ArrayList<>();
        log.debug("Пытаюсь найти сотрудника с именем {}.", name);
        for (Employer employer : employers.values()) {
            if (employer.getName().equals(name)) {
                log.debug("Успех! Сотрудник с именем {} найден", name);
                foundUsersByName.add(employer);
            } else {
                log.debug("Отказ! Сотрудник с именем {} не найден", name);
                return null;
            }
        }
        return foundUsersByName;
    }

    public List<Employer> getUserByLastName(String lastName) {
        if (lastName == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        List<Employer> foundUsersByLastName = new ArrayList<>();
        log.debug("Пытаюсь найти сотрудников с фамилией {}.", lastName);
        for (Employer employer : employers.values()) {
            if (employer.getLastName().equals(lastName)) {
                log.debug("Успех! Сотрудник с фамилией {} найден", lastName);
                foundUsersByLastName.add(employer);
            } else {
                log.debug("Отказ! Сотрудник с фамилией {} не найден", lastName);
                return null;
            }
        }
        return foundUsersByLastName;
    }

    public List<Employer> getUserByPatronymic(String patronymic) {
        if (patronymic == null) {
            throw  new ValidationException("Ошибка: попытка поиска пустого значения.");
        }
        List<Employer> foundUsersByPatronymic = new ArrayList<>();
        log.debug("Пытаюсь найти пользователя с отчеством {}.", patronymic);
        for (Employer employer : employers.values()) {
            if (employer.getPatronymic().equals(patronymic)) {
                log.debug("Успех! Сотрудник с отчеством {} найден", patronymic);
                foundUsersByPatronymic.add(employer);
            } else {
                log.debug("Отказ! Сотрудник с отчеством {} не найден", patronymic);
                return null;
            }
        }
        return foundUsersByPatronymic;
    }

    public List<Employer> getUserByBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw  new ValidationException("Ошибка: Неверный формат даты, либо попытка поиска пустого значения. Введите дату в формате: ДД.ММ.ГГГГ!");
        }
        List<Employer> foundUsersByBirthday = new ArrayList<>();
        log.debug("Пытаюсь найти сотрудника по дате рождения {}.", birthday);
        for (Employer employer : employers.values()) {
            if (employer.getBirthday().equals(birthday)) {
                log.debug("Успех! Сотрудник с датой рождения {} найден.", birthday);
                foundUsersByBirthday.add(employer);
            } else {
                log.debug("Отказ! Сотрудник с датой рождения {} не найден.", birthday);
                return null;
            }
        }
        return foundUsersByBirthday;
    }

    private void userValidation(Employer employer) {
        if (employer.getBirthday().isAfter(LocalDate.now().minusYears(18))) {
            throw new ValidationException("Сотрудник не может быть младше 18 лет.");
        }
        if (employer.getLastVacationDate().isBefore(employer.getHired())) {
            throw new ValidationException("Сотрудник не мог находиться в отпуске до того, как был нанят.");
        }
        if (employer.getHired() == null) {
            employer.setHired(LocalDate.now());
        }
        if (employer.getLastVacationDate() == null) {
            employer.setLastVacationDate(employer.getHired());
        }
        if (employer.getFired() == null || employer.getFired().isAfter(LocalDate.now())) {
            employer.setStatus(Status.WORKING);
        } else {
            employer.setStatus(Status.FIRED);
        }
    }
}
