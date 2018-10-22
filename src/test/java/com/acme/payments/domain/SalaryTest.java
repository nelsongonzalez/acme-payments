package com.acme.payments.domain;

import com.acme.payments.domain.impl.HourlySalary;
import com.acme.payments.domain.impl.HourlySalaryTable;
import com.acme.payments.domain.impl.HourlyWorkEvent;
import com.acme.payments.domain.impl.Money;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SalaryTest {

    @Test
    public void shoulGetPaymentAmount() {
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
        List<WorkEvent> workedTime = List.of(
                new HourlyWorkEvent(DayOfWeek.MONDAY, tenOClock, twelveOClock)
        );
        var weeklyPayment = new HourlySalary(hourlySalaryTable);

        assertThat(weeklyPayment.calculate(workedTime), is(thirtyUsd));
    }
}
