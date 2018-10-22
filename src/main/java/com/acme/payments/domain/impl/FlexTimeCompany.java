package com.acme.payments.domain.impl;

import com.acme.payments.domain.Company;
import com.acme.payments.domain.Employee;
import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.Salary;

import java.util.Objects;

public final class FlexTimeCompany implements Company {

    private final String name;

    private final Salary salary;

    public FlexTimeCompany(String name, Salary salary) {
        Objects.requireNonNull(name, "name must be not null.");
        Objects.requireNonNull(salary, "salary must be not null.");
        this.name = name;
        this.salary = salary;
    }

    @Override
    public MonetaryAmount payTo(Employee employee) {
        Objects.requireNonNull(employee, "employee must be not null.");
        return salary.calculate(employee.getWorkSchedule());
    }

    @Override
    public String toString() {
        return name;
    }
}
