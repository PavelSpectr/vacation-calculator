package com.neoflex.vacationcalculator.storage;

import com.neoflex.vacationcalculator.model.Employer;

import java.util.List;

public interface EmployerStorage {
    Employer createEmployer(Employer employer);

    Employer updateEmployer(Employer employer);

    void deleteEmployer(Employer employer);

    List<Employer> getAllEmployers();

    Employer getEmployerByEmail(String email);

    List<Employer> getEmployerByFullNameAndBirthday(Employer employer);
}
