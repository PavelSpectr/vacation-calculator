package com.neoflex.vacationcalculator.storage;

import com.neoflex.vacationcalculator.model.User;

import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> getAllUsers();

    User getUserByName(String name);

    User getUserByLastName(String lastName);

    User getUserByPatronymic(String patronymic);

    User getUserByEmail(String email);
}
