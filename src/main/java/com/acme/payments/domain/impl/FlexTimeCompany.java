package com.acme.payments.domain.impl;

import com.acme.payments.domain.Company;
import com.acme.payments.domain.Employee;
import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.Payment;

public final class FlexTimeCompany implements Company {

    private final String name;

    private final Payment weeklyPayment;

    public FlexTimeCompany(String name, Payment weeklyPayment) {
        this.name = name;
        this.weeklyPayment = weeklyPayment;
    }

    @Override
    public MonetaryAmount payTo(Employee employee) {
        return weeklyPayment.totalSalary(employee.getWorkedTime());
    }

    @Override
    public String toString() {
        return name;
    }
}
