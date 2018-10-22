package com.acme.payments.domain;

public interface SalaryTable {

    MonetaryAmount workTimeSalary(WorkTime workTime);

    interface Entry {

        boolean equalDayOfWeek(WorkTime workTime);

        MonetaryAmount partialWorkTimeSalary(WorkTime workTime);
    }
}
