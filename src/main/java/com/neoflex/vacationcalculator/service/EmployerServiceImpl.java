package com.neoflex.vacationcalculator.service;

import com.neoflex.vacationcalculator.model.Employer;
import com.neoflex.vacationcalculator.storage.EmployerStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {
    private final EmployerStorage employerStorage;

    @Override
    public Employer createEmployer(Employer employer) {
        return employerStorage.createEmployer(employer);
    }

    @Override
    public Employer updateEmployer(Employer employer) {
        return employerStorage.updateEmployer(employer);
    }

    @Override
    public void deleteEmployer(Employer employer) {
        employerStorage.deleteEmployer(employer);
    }

    @Override
    public List<Employer> getAllEmployers() {
        return null;
    }

    @Override
    public Employer getEmployerByEmail(String email) {
        return employerStorage.getEmployerByEmail(email);
    }

    @Override
    public List<Employer> getEmployerByFullNameAndBirthday(String name, String lastName, String patronymic, LocalDate birthday) {
        return employerStorage.getEmployerByFullNameAndBirthday(name, lastName, patronymic, birthday);
    }
}
