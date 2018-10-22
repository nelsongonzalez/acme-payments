package com.acme.payments.application.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.impl.FlexTimeCompany;
import com.acme.payments.domain.impl.HourlySalaryTable;
import com.acme.payments.application.EmployeeSalary;

public final class HourlyEmployeeSalary implements EmployeeSalary {

    private final SalaryTable salaryTable;

    public HourlyEmployeeSalary() {
        salaryTable = new HourlySalaryTable.Builder().defaultSalaryTable().build();
    }

    @Override
    public MonetaryAmount calculate(Employee employee) {
        var weeklyPayment = new com.acme.payments.domain.impl.HourlySalary(salaryTable);
        var acmeCompany = new FlexTimeCompany("ACME", weeklyPayment);
        return acmeCompany.payTo(employee);
    }

}
