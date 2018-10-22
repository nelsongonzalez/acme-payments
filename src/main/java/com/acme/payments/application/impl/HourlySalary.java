package com.acme.payments.application.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.impl.FlexTimeCompany;
import com.acme.payments.domain.impl.HourlyPayment;
import com.acme.payments.domain.impl.HourlySalaryTable;
import com.acme.payments.application.Salary;

public final class HourlySalary implements Salary {

    private final SalaryTable salaryTable;

    public HourlySalary() {
        salaryTable = new HourlySalaryTable.Builder().defaultSalaryTable().build();
    }

    @Override
    public MonetaryAmount calculate(Employee employee) {
        var weeklyPayment = new HourlyPayment(salaryTable);
        var acmeCompany = new FlexTimeCompany("ACME", weeklyPayment);
        return acmeCompany.payTo(employee);
    }

}
