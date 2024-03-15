package com.neoflex.vacationcalculator.storage;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.neoflex.vacationcalculator.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserStorageImpl implements UserStorage {
    private final Map<UUID, User> users = new HashMap<>();
    private ObjectIdGenerators.UUIDGenerator uuid;

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return null;
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

    private void isValid(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now().minusYears(18))) {
            throw new ValidationException("Работник не может быть младше 18 лет.");
        }
    }
}
