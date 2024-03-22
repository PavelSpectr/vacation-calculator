package com.neoflex.vacationcalculator.service;

import com.neoflex.vacationcalculator.model.Employer;
import com.neoflex.vacationcalculator.storage.EmployerStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        LocalDate startVac;
        LocalDate endVac;
        long vacCountDays;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormat = "\\d{2}.\\d{2}.\\d{4}";
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите дату начала отпуска сотрудника в формате ДД.ММ.ГГГГ:");
            startVac = LocalDate.parse(scanner.next(dateFormat), formatter);
            System.out.println("Введите дату последнего отпуска сотрудника в формате ДД.ММ.ГГГГ:");
            endVac = LocalDate.parse(scanner.next(dateFormat), formatter);
            vacCountDays = Duration.between(startVac, endVac).toDays();
        } catch (ValidationException e) {
            throw new ValidationException("Неверный формат даты!");
        }
        if (vacCountDays > 0) {
            System.out.println("Данные введены верно.");
            employer.setLastVacationDate(endVac);
        } else {
            System.out.println("Ошибка! Даты введены не верно. Проверьте корректность введенных данных и повторите попытку.");
            vacationCalculator(email);
        }
        double avgSalaryPerDay = employer.getSalary() / 29.3;
        return (long) avgSalaryPerDay * vacCountDays;
    }
}
