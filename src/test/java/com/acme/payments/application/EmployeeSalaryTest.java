package com.acme.payments.application;

import com.acme.payments.domain.impl.Money;
import com.acme.payments.application.impl.DataFileEmployeeSchedule;
import com.acme.payments.application.impl.HourlyEmployeeSalary;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Currency;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EmployeeSalaryTest {

    @Test
    public void shouldCalculateSalaries() {
        var pathAsString = EmployeeSchedule.class.getResource("/fileRene.txt").getPath();
        var employeeSchedule = new DataFileEmployeeSchedule(Path.of(pathAsString));
        var employees = employeeSchedule.scheduling();
        var salary = new HourlyEmployeeSalary();

        var amount = salary.calculate(employees.get(0));

        assertThat(amount, is(new Money(new BigDecimal("215"), Currency.getInstance("USD"))));
    }
}
