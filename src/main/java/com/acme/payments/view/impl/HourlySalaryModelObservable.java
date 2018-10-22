package com.acme.payments.view.impl;

import com.acme.payments.application.impl.DataFileEmployeeSchedule;
import com.acme.payments.application.impl.HourlySalary;
import com.acme.payments.application.impl.MinLinesEmployeeSchedule;
import com.acme.payments.view.Observable;
import com.acme.payments.view.SalaryModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Path;
import java.util.Objects;

public final class HourlySalaryModelObservable implements SalaryModel, Observable {

    private final PropertyChangeSupport support;

    private final Path path;

    public HourlySalaryModelObservable(Path path) {
        Objects.requireNonNull(path, "path must be not null.");
        support = new PropertyChangeSupport(this);
        this.path = path;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        support.addPropertyChangeListener(propertyChangeListener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        support.removePropertyChangeListener(propertyChangeListener);
    }

    @Override
    public void calculate() {
        var employees = new MinLinesEmployeeSchedule(new DataFileEmployeeSchedule(path), 5).read();
        var salary = new HourlySalary();
        employees.forEach(employee -> {
            var payToEmployee = salary.calculate(employee);
            support.firePropertyChange("", "",
                    String.format("The amount to pay %s is: %.0f %s",
                            employee.getName(), payToEmployee.getAmount(), payToEmployee.getCurrency()));
        });
    }

}
