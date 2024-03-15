package com.neoflex.vacationcalculator.model;

import com.neoflex.vacationcalculator.validation.NoSpacesDigitsSymbols;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class User {
    @NotBlank(message = "Ошибка присвоения идентификатора.")
    private UUID id;

    @NotBlank(message = "Поле имени не может быть пустым.")
    private String name;

    @NotBlank(message = "Поле фамилии не может быть пустым.")
    @NoSpacesDigitsSymbols
    private String lastName;

    @NoSpacesDigitsSymbols
    private String patronymic;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotBlank(message = "Не верный формат даты, либо поле не заполнено. Введите дату в формате ДД.ММ.ГГГГ!")
    private LocalDate birthday;

    @Email(message = "Неверный формат электронной почты.", regexp = "@*.*")
    private String email;

    @NotBlank(message = "Поле зарплаты не может быть пустым.")
    @Positive(message = "Зарплата не может быть меньше или равной нулю.")
    private Integer salary;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @PastOrPresent
    private LocalDate lastVacationDate;

    @NotBlank
    private Status status;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @PastOrPresent
    private LocalDate hired;

    public User(UUID id, String name, String lastName, LocalDate birthday, String email, Integer salary, LocalDate lastVacationDate, Status status) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.salary = salary;
        this.lastVacationDate = lastVacationDate;
        this.status = status;
    }
}
