package com.acme.payments.domain;

public interface SalaryTable {

    Money salaryOf(WorkTime workTime);

    interface Entry {

        long hoursOf(WorkTime workTime);

        Money salaryBy(long hours);
    }
}
