package com.acme.payments.domain.impl;

import com.acme.payments.domain.Company;
import com.acme.payments.domain.Employee;
import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.Payment;

import java.util.Objects;

public final class FlexTimeCompany implements Company {

    private final String name;

    private final Payment payment;

    public FlexTimeCompany(String name, Payment payment) {
        Objects.requireNonNull(name, "name must be not null.");
        Objects.requireNonNull(payment, "payment must be not null.");
        this.name = name;
        this.payment = payment;
    }

    @Override
    public MonetaryAmount payTo(Employee employee) {
        Objects.requireNonNull(employee, "employee must be not null.");
        return payment.totalSalary(employee.getWorkTimes());
    }

    @Override
    public String toString() {
        return name;
    }
}
