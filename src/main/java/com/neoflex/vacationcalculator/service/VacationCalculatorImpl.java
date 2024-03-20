package com.neoflex.vacationcalculator.service;

import com.neoflex.vacationcalculator.model.Employer;
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
public class VacationCalculatorImpl implements VacationCalculator {

    @Override
    public void vacationCalculator(Employer employer) {
        String startVac;
        String endVac;
        long vacCountDays;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormat = "\\d{2}.\\d{2}.\\d{4}";
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите дату начала отпуска сотрудника в формате ДД.ММ.ГГГГ:");
            startVac = scanner.next(dateFormat);
            System.out.println("Введите дату последнего отпуска сотрудника в формате ДД.ММ.ГГГГ:");
            endVac = scanner.next(dateFormat);
            vacCountDays = Duration.between(LocalDate.parse(endVac, formatter), LocalDate.parse(startVac, formatter)).toDays();
        } catch (ValidationException e) {
            throw new ValidationException("Неверный формат даты!");
        }
        long countWorkDays;
    }

    /*private LocalDate getDate() throws IllegalArgumentException {
        try (Scanner scanner = new Scanner(System.in)) {
            int day, month, year;
            System.out.println("Введите дату начала отпуска сотрудника в формате ДД.ММ.ГГГГ:");
            String date = scanner.nextLine();
            if (date != null && date.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                System.out.println("Введенная дата соответствует формату ДД.ММ.ГГГГ");
                int[] partsFrom = Stream.of(date.split("\\.", 2))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                if (partsFrom[0] > 0) {

                }
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
    }*/
}
