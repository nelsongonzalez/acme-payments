package com.acme.payments.view.impl;

import com.acme.payments.application.impl.DataFileEmployeeSchedule;
import com.acme.payments.application.impl.HourlyEmployeeSalary;
import com.acme.payments.application.impl.MinLinesEmployeeSchedule;
import com.acme.payments.view.Observable;
import com.acme.payments.view.EmployeeSalaryModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Path;
import java.util.Objects;

public final class HourlyEmployeeSalaryModel implements EmployeeSalaryModel, Observable {

    private static final int AT_LEAST_FIVE_LINES = 5;

    private final PropertyChangeSupport support;

    private final Path path;

    public HourlyEmployeeSalaryModel(Path path) {
        Objects.requireNonNull(path, "path must be not null.");
        support = new PropertyChangeSupport(this);
        this.path = path;
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        support.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        support.removePropertyChangeListener(observer);
    }

    @Override
    public void calculate() {
        var employees = new MinLinesEmployeeSchedule(new DataFileEmployeeSchedule(path), AT_LEAST_FIVE_LINES).scheduling();
        var salary = new HourlyEmployeeSalary();
        employees.forEach(employee -> {
            var payToEmployee = salary.calculate(employee);
            support.firePropertyChange("", "",
                    String.format("The amount to pay %s is: %.0f %s",
                            employee.getName(), payToEmployee.getAmount(), payToEmployee.getCurrency()));
        });
    }

}
