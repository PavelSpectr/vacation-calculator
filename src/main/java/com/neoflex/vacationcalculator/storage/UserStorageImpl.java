package com.neoflex.vacationcalculator.storage;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    private ObjectIdGenerators.UUIDGenerator uuid;

    @Override
    public User createUser(User user) {
        log.debug("Создание пользователя...");
        if (user.getBirthday().isAfter(LocalDate.now().minusYears(18))) {
            throw new ValidationException("Работник не может быть младше 18 лет.");
        }
        user.setId(UUID.randomUUID());
        users.put(user.getId(), user);
        log.debug("Пользователь успешно добавлен: {} {}", user.getLastName(), user.getName());

        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new ValidationException("Такого пользователя не существует.");
        }
        users.put(user.getId(), user);
        log.debug("Пользователь успешно изменен: {} {}", user.getLastName(), user.getName());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new ValidationException("Такого пользователя не существует.");
        }
        users.remove(user.getId(), user);
        log.debug("Пользователь успешно удален: {} {}", user.getLastName(), user.getName());
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserByName(String name) {
        log.debug("Пытаюсь найти пользователя с именем {}.", name);
        for (User user : users.values()) {
            if (user.getName().equals(name)) {
                log.debug("Успех! Пользователь с именем {} найден", name);
                return user;
            } else {
                log.debug("Отказ! Пользователь с именем {} не найден", name);
                return null;
            }
        }
    }

    @Override
    public User getUserByLastName(String lastName) {
        return null;
    }

    @Override
    public User getUserByPatronymic(String patronymic) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}
