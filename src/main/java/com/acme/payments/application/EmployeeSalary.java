package com.acme.payments.application;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.MonetaryAmount;

public interface EmployeeSalary {

    MonetaryAmount calculate(Employee employee);
}
