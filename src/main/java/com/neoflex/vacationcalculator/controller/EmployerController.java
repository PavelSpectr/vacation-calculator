package com.neoflex.vacationcalculator.controller;

import com.neoflex.vacationcalculator.model.Employer;
import com.neoflex.vacationcalculator.service.EmployerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employer")
public class EmployerController {
    public final EmployerService employerService;

    @PostMapping
    public Employer createEmployer(@Valid @RequestBody Employer employer) {
        log.debug("Start CreateEmployer controller...");
        return employerService.createEmployer(employer);
    }

    @PutMapping
    public Employer updateEmployer(@Valid @RequestBody Employer employer) {
        log.debug("Start UpdateEmployer controller...");
        return employerService.updateEmployer(employer);
    }

    @DeleteMapping
    public void deleteEmployer(@Valid @RequestBody Employer employer) {
        log.debug("Start DeleteEmployer controller...");
        employerService.deleteEmployer(employer);
    }

    @GetMapping
    public List<Employer> getAllEmployers() {
        log.debug("Start GetAllEmployers controller...");
        return employerService.getAllEmployers();
    }

    @GetMapping("/{email}")
    public Employer getEmployerByEmail(@PathVariable("email") String email) {
        log.debug("Start GetEmployerByEmail controller...");
        return employerService.getEmployerByEmail(email);
    }

    @GetMapping("/{name}&{lastname}&{patronymic}&{birthday}")
    public List<Employer> getEmployerByFullNameAndBirthday(@PathVariable(value = "name", required = false) String name,
                                                           @PathVariable(value = "lastname", required = false) String lastName,
                                                           @PathVariable(value = "patronymic", required = false) String patronymic,
                                                           @PathVariable(value = "birthday", required = false) LocalDate birthday) {
        log.debug("Start GetEmployerByFullNameAndBirthday controller...");
        return employerService.getEmployerByFullNameAndBirthday(name, lastName, patronymic, birthday);
    }
}
