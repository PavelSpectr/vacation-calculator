package com.neoflex.vacationcalculator.controller;

import com.neoflex.vacationcalculator.service.VacationCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/calculator")
public class CalculatorController {
    private final VacationCalculatorService calculatorService;

    @GetMapping("/{employer}")
    public long vacationCalculator(@Valid @PathVariable("employer") String email) {
        log.debug("Start VacationCalculator controller...");
        return calculatorService.vacationCalculator(email);
    }
}
