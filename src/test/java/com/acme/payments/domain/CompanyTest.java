package com.acme.payments.domain;

import com.acme.payments.domain.impl.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompanyTest {

    @Test
    public void shouldCalcAmountOfMoney() {
        var hour9Minute1 = LocalTime.of(9, 1);
        var hour10 = LocalTime.of(10, 0);
        var hour12 = LocalTime.of(12, 0);
        var hour18 = LocalTime.of(18, 0);
        var usd15 = new Money(new BigDecimal("15"), Currency.getInstance("USD"));
        var usd30 = new Money(new BigDecimal("30"), Currency.getInstance("USD"));

        List<SalaryTable.Entry> salaryList = List.of(
                new HourlySalaryTable.Entry(DayOfWeek.MONDAY, hour9Minute1, hour18, usd15)
        );
        var hourlySalaryTable = new HourlySalaryTable(salaryList);

        List<WorkTime> workedTime = List.of(
                new HourlyWorkTime(DayOfWeek.MONDAY, hour10, hour12)
        );
        var reneEmployee = new Executive(workedTime);

        var weeklyPayment = new WeeklyPayment(hourlySalaryTable);
        var acmeCompany = new FlexTimeCompany("ACME", weeklyPayment);
        var payToRene = acmeCompany.payTo(reneEmployee);

        assertThat(payToRene, is(usd30));
    }
}
