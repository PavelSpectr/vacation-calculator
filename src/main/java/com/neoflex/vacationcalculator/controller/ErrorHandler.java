package com.neoflex.vacationcalculator.controller;

import com.neoflex.vacationcalculator.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.neoflex.vacationcalculator.model.ErrorResponse;

@RestControllerAdvice(assignableTypes = {EmployerController.class, VacationCalculatorController.class})
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBadRequest(final NullPointerException e) {
        return new ErrorResponse("404 - Искомый объект не найден", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFound(final ValidationException e) {
        return new ErrorResponse("400 - Ошибка валидации", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(final InternalError e) {
        return new ErrorResponse("500 - Внутренняя ошибка сервера", e.getMessage());
    }
}
