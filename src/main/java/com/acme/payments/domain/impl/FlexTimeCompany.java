package com.acme.payments.domain.impl;

import com.acme.payments.domain.Company;
import com.acme.payments.domain.Employee;
import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.Payment;

import java.util.Objects;

public final class FlexTimeCompany implements Company {

    private final String name;

    private final Payment weeklyPayment;

    public FlexTimeCompany(String name, Payment weeklyPayment) {
        Objects.requireNonNull(name, "name must be not null.");
        Objects.requireNonNull(weeklyPayment, "weeklyPayment must be not null.");
        this.name = name;
        this.weeklyPayment = weeklyPayment;
    }

    @Override
    public MonetaryAmount payTo(Employee employee) {
        Objects.requireNonNull(employee, "employee must be not null.");
        return weeklyPayment.totalSalary(employee.getWorkedTime());
    }

    @Override
    public String toString() {
        return name;
    }
}
