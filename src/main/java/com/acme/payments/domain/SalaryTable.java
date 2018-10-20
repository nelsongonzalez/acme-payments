package com.acme.payments.domain;

public interface SalaryTable {

    MonetaryAmount workTimeSalary(WorkTime workTime);

    interface Entry {

        MonetaryAmount partialWorkTimeSalary(WorkTime workTime);
    }
}
