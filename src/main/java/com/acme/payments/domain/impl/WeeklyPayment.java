package com.acme.payments.domain.impl;

import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.Payment;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.WorkTime;

import java.util.List;
import java.util.Objects;

public final class WeeklyPayment implements Payment {

    private final SalaryTable salaryTable;

    public WeeklyPayment(SalaryTable salaryTable) {
        Objects.requireNonNull(salaryTable, "salaryTable must be not null.");
        this.salaryTable = salaryTable;
    }

    @Override
    public MonetaryAmount totalSalary(List<WorkTime> workTimeList) {
        Objects.requireNonNull(workTimeList, "workTimeList must be not null.");
        return workTimeList.stream()
                .map(salaryTable::workTimeSalary)
                .reduce(MonetaryAmount::add)
                .orElse(Money.NOTHING);
    }
}
