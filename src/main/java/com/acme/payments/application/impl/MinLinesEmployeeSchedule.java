package com.acme.payments.application.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.application.EmployeeSchedule;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class MinLinesEmployeeSchedule implements EmployeeSchedule {

    private final DataFileEmployeeSchedule employeeSchedule;

    private final int minLines;

    public MinLinesEmployeeSchedule(DataFileEmployeeSchedule employeeSchedule, int minLines) {
        this.employeeSchedule = employeeSchedule;
        this.minLines = minLines;
    }

    @Override
    public List<Employee> read() {
        try {
            long countLines = Files.lines(employeeSchedule.path).count();
            if (countLines < minLines) {
                throw new IllegalArgumentException(String.format("The file should have at least %d lines.", minLines));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File data is invalid.");
        }
        return employeeSchedule.read();
    }
}
