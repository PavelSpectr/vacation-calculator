package com.neoflex.vacationcalculator.service;

import com.neoflex.vacationcalculator.model.Employer;
import com.neoflex.vacationcalculator.storage.EmployerStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacationCalculatorServiceImpl implements VacationCalculatorService {
    private final EmployerStorage employerStorage;

    @Override
    public long vacationCalculator(String email) {
        log.debug("Start VacationCalculatorService service...");
        Employer employer = employerStorage.getEmployerByEmail(email);
        System.out.println("Введите дату начала отпуска сотрудника в формате ДД.ММ.ГГГГ:");
        LocalDate startVac = dateParser();
        System.out.println("Введите дату крайнего дня отпуска сотрудника в формате ДД.ММ.ГГГГ:");
        LocalDate endVac = dateParser();
        long vacCountDays = Duration.between(startVac, endVac).plusDays(1).toDays();
        if (vacCountDays > 0) {
            System.out.println("Данные введены верно.");
            employer.setLastVacationDate(endVac);
        } else {
            System.out.println("Ошибка! Даты введены не верно. Проверьте корректность введенных данных и повторите попытку.");
            vacationCalculator(email);
        }
        double avgSalaryPerDay = employer.getSalary() / 29.3;
        System.out.println("Сумма отпускных составляет:");
        return (long) avgSalaryPerDay * vacCountDays;
    }

    private LocalDate dateParser() throws InputMismatchException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormat = "\\d{2}.\\d{2}.\\d{4}";
        LocalDate date;
        try (Scanner scanner = new Scanner(System.in)) {
            date = LocalDate.parse(scanner.next(dateFormat), formatter);
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Ошибка! Даты введены не верно. Проверьте корректность введенных данных и повторите попытку. " + e.getMessage());
        }
        return date;
    }
}