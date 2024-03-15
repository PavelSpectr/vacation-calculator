package com.neoflex.vacationcalculator.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NoSpacesDigitsSymbolsValidator.class)
public @interface NoSpacesDigitsSymbols {
    String message() default "Строка должна быть без пробелов, цифр, символов, а так же не может быть пустой.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}