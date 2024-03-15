package com.neoflex.vacationcalculator.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String error;
    private final String description;
}
