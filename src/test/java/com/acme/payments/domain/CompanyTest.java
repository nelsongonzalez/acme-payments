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
        var nineOClock = LocalTime.of(9, 0);
        var tenOClock = LocalTime.of(10, 0);
        var twelveOClock = LocalTime.of(12, 0);
        var eighteenOClock = LocalTime.of(18, 0);
        var fifteenUsd = new Money(new BigDecimal("15"), Currency.getInstance("USD"));
        var thirtyUsd = new Money(new BigDecimal("30"), Currency.getInstance("USD"));

        List<SalaryTable.Entry> salaryList = List.of(
                new HourlySalaryTable.Entry(DayOfWeek.MONDAY, nineOClock, eighteenOClock, fifteenUsd)
        );
        var hourlySalaryTable = new HourlySalaryTable(salaryList);

        List<WorkTime> workedTime = List.of(
                new HourlyWorkTime(DayOfWeek.MONDAY, tenOClock, twelveOClock)
        );
        var reneEmployee = new Executive("Rene", workedTime);

        var weeklyPayment = new HourlyPayment(hourlySalaryTable);
        var acmeCompany = new FlexTimeCompany("ACME", weeklyPayment);
        var payToRene = acmeCompany.payTo(reneEmployee);

        assertThat(payToRene, is(thirtyUsd));
    }
}
