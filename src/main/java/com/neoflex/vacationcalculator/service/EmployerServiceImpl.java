package com.neoflex.vacationcalculator.service;

import com.neoflex.vacationcalculator.model.Employer;
import com.neoflex.vacationcalculator.storage.EmployerStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {
    private final EmployerStorage employerStorage;

    @Override
    public Employer createEmployer(Employer employer) {
        log.debug("Start CreateEmployer service...");
        return employerStorage.createEmployer(employer);
    }

    @Override
    public Employer updateEmployer(Employer employer) {
        log.debug("Start UpdateEmployer service...");
        return employerStorage.updateEmployer(employer);
    }

    @Override
    public void deleteEmployer(Employer employer) {
        log.debug("Start DeleteEmployer service...");
        employerStorage.deleteEmployer(employer);
    }

    @Override
    public List<Employer> getAllEmployers() {
        log.debug("Start GetAllEmployers service...");
        return employerStorage.getAllEmployers();
    }

    @Override
    public Employer getEmployerByEmail(String email) {
        log.debug("Start GetEmployerByEmail service...");
        return employerStorage.getEmployerByEmail(email);
    }

    @Override
    public List<Employer> getEmployerByFullNameAndBirthday(String name, String lastName, String patronymic, LocalDate birthday) {
        log.debug("Start GetEmployerByFullNameAndBirthday service...");
        return employerStorage.getEmployerByFullNameAndBirthday(name, lastName, patronymic, birthday);
    }
}
