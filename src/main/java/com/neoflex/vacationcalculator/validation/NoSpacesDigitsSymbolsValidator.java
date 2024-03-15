package com.neoflex.vacationcalculator.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoSpacesDigitsSymbolsValidator implements ConstraintValidator<NoSpacesDigitsSymbols, String> {
    @Override
    public void initialize(NoSpacesDigitsSymbols constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^[a-zA-Zа-яА-Я]+$");
    }
}
