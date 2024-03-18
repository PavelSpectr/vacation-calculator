package com.neoflex.vacationcalculator.service;

import com.neoflex.vacationcalculator.model.User;
import com.neoflex.vacationcalculator.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacationCalculatorImpl implements VacationCalculator {
    private final UserStorage userStorage;

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userStorage.deleteUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userStorage.getUserByEmail(email);
    }

    @Override
    public List<User> getUserByFullNameAndBirthday(User user) {
        return userStorage.getUserByFullNameAndBirthday(user);
    }

    public void vacationCalculator(User user) {
            System.out.println("Введите дату начала отпуска сотрудника в формате ДД.ММ.ГГГГ:");
            LocalDate startVac = LocalDate.of(dayFrom, monthFrom, yearFrom);
            LocalDate endVac = LocalDate.of(dayTo, monthTo, yearTo);
            long vacPeriod = Duration.between(startVac, endVac).toDays();
    }

    private LocalDate getDate() throws IllegalArgumentException {
        try (Scanner scanner = new Scanner(System.in)) {
            int day, month, year;
            String date = scanner.nextLine();
            if (date.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                System.out.println("Введенная дата соответствует формату ДД.ММ.ГГГГ");
                int[] partsFrom = Stream.of(date.split("\\.", 2))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                day = partsFrom[0];
                month = partsFrom[1];
                year = partsFrom[2];
                return LocalDate.of(day, month, year);
            } else {
                System.out.println("Введенная дата не соответствует формату ДД.ММ.ГГГГ! Повторите попытку");
                getDate();
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return null;
    }
}
