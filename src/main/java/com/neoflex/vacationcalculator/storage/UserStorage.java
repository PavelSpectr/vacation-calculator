package com.neoflex.vacationcalculator.storage;

import com.neoflex.vacationcalculator.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    List<User> getUserByFullNameAndBirthday(User user);
}
