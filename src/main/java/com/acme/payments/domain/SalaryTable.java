package com.acme.payments.domain;

public interface SalaryTable {

    MonetaryAmount workEventSalary(WorkEvent workEvent);

    interface Entry {

        boolean equalDayOfWeek(WorkEvent workEvent);

        MonetaryAmount partialWorkEventSalary(WorkEvent workEvent);
    }
}
