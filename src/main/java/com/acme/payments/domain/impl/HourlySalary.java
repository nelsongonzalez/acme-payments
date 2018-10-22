package com.acme.payments.domain.impl;

import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.Salary;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.WorkEvent;

import java.util.List;
import java.util.Objects;

public final class HourlySalary implements Salary {

    private final SalaryTable salaryTable;

    public HourlySalary(SalaryTable salaryTable) {
        Objects.requireNonNull(salaryTable, "salaryTable must be not null.");
        this.salaryTable = salaryTable;
    }

    @Override
    public MonetaryAmount calculate(List<WorkEvent> workSchedule) {
        Objects.requireNonNull(workSchedule, "workSchedule must be not null.");
        return workSchedule.stream()
                .map(salaryTable::workEventSalary)
                .reduce(MonetaryAmount::add)
                .orElse(Money.NOTHING);
    }
}
