package com.acme.payments.domain;

import com.acme.payments.domain.impl.HourlySalaryTable;
import com.acme.payments.domain.impl.HourlyWorkTime;
import com.acme.payments.domain.impl.Money;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SalaryTableTest {

    @Test
    public void shouldGetSalaryOfAnHour() {
        var nineOClock = LocalTime.of(9, 0);
        var tenOClock = LocalTime.of(10, 0);
        var elevenOClock = LocalTime.of(11, 0);
        var eighteenOClock = LocalTime.of(18, 0);
        var fifteenUsd = new Money(new BigDecimal("15"), Currency.getInstance("USD"));

        List<SalaryTable.Entry> salaryList = List.of(
                new HourlySalaryTable.Entry(DayOfWeek.MONDAY, nineOClock, eighteenOClock, fifteenUsd)
        );
        var hourlySalaryTable = new HourlySalaryTable(salaryList);
        var hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, tenOClock, elevenOClock);

        assertThat(hourlySalaryTable.workTimeSalary(hourlyWorkTime), is(fifteenUsd));
    }

    @Test
    public void shouldGetSalaryOfTwoHours() {
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
        var hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, tenOClock, twelveOClock);

        assertThat(hourlySalaryTable.workTimeSalary(hourlyWorkTime), is(thirtyUsd));
    }

    @Test
    public void shouldGetSalaryOnlyForHoursInTheRange() {
        var nineOClock = LocalTime.of(9, 0);
        var seventeenOClock = LocalTime.of(17, 0);
        var eighteenOClock = LocalTime.of(18, 0);
        var nineteenOClock = LocalTime.of(19, 0);
        var fifteenUsd = new Money(new BigDecimal("15"), Currency.getInstance("USD"));

        List<SalaryTable.Entry> salaryList = List.of(
                new HourlySalaryTable.Entry(DayOfWeek.MONDAY, nineOClock, eighteenOClock, fifteenUsd)
        );
        var hourlySalaryTable = new HourlySalaryTable(salaryList);
        var hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, seventeenOClock, nineteenOClock);

        assertThat(hourlySalaryTable.workTimeSalary(hourlyWorkTime), is(fifteenUsd));
    }
}
