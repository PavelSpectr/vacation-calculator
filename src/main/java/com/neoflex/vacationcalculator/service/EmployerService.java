package com.neoflex.vacationcalculator.service;

import com.neoflex.vacationcalculator.model.Employer;
import com.neoflex.vacationcalculator.model.User;

import java.util.List;

public interface EmployerService {
    Employer createEmployer(Employer employer);

    Employer updateEmployer(Employer employer);

    void deleteEmployer(Employer employer);

    List<Employer> getAllEmployers();

    Employer getEmployerByEmail(String email);

    List<Employer> getEmployerByFullNameAndBirthday(Employer employer);
}
