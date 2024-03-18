package com.neoflex.vacationcalculator.storage;

import com.neoflex.vacationcalculator.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> getAllUsers();

    List<User> getUserByName(String name);

    List<User> getUserByLastName(String lastName);

    List<User> getUserByPatronymic(String patronymic);

    List<User> getUserByBirthday(LocalDate birthday);

    List<User> getUserByFullNameAndBirthday(User user, String name, String lastName, String patronymic, LocalDate birthday);

    User getUserByEmail(String email);
}
