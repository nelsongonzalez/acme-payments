package com.acme.payments.domain;

public interface SalaryTable {

    MonetaryAmount salaryOf(WorkTime workTime);

    interface Entry {

        long hoursOf(WorkTime workTime);

        MonetaryAmount salaryBy(long hours);
    }
}
