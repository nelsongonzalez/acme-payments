package com.acme.payments.domain.impl;

import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.Payment;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.WorkTime;

import java.util.List;

public final class WeeklyPayment implements Payment {

    private final SalaryTable salaryTable;

    public WeeklyPayment(SalaryTable salaryTable) {
        this.salaryTable = salaryTable;
    }

    @Override
    public MonetaryAmount totalSalary(List<WorkTime> workTimeList) {
        // TODO parameterize currency
        return workTimeList.stream()
                .map(salaryTable::workTimeSalary)
                .reduce(MonetaryAmount::add)
                .orElse(Money.NOTHING);
    }
}
