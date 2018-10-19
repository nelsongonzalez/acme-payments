package com.acme.payments.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SalaryTableTest {

    @Test
    public void shouldCalcularHours() {
        SalaryTable.Entry salaryEntry = new HourlySalaryTable.Entry(DayOfWeek.MONDAY, LocalTime.of(9, 1), LocalTime.of(18, 0), UsdMoney.ZERO);
        WorkTime hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0));
        assertThat(salaryEntry.hoursOf(hourlyWorkTime), is(3L));
    }

    @Test
    public void shouldCalcularHours2() {
        SalaryTable.Entry salaryEntry = new HourlySalaryTable.Entry(DayOfWeek.MONDAY, LocalTime.of(9, 1), LocalTime.of(18, 0), UsdMoney.ZERO);
        WorkTime hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));
        assertThat(salaryEntry.hoursOf(hourlyWorkTime), is(1L));
    }

    @Test
    public void shouldGetSalaryOfOneHour() {
        List<SalaryTable.Entry> salaryList = List.of(
                new HourlySalaryTable.Entry(DayOfWeek.MONDAY, LocalTime.of(9, 1), LocalTime.of(18, 0), new UsdMoney(new BigDecimal("15")))
        );
        SalaryTable hourlySalaryTable = new HourlySalaryTable(salaryList);

        WorkTime hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 0));

        assertThat(hourlySalaryTable.salaryOf(hourlyWorkTime), is(new UsdMoney(new BigDecimal("15"))));
    }

    @Test
    public void shouldGetSalaryOfTwoHours() {
        List<SalaryTable.Entry> salaryList = List.of(
                new HourlySalaryTable.Entry(DayOfWeek.MONDAY, LocalTime.of(9, 1), LocalTime.of(18, 0), new UsdMoney(new BigDecimal("15")))
        );
        SalaryTable hourlySalaryTable = new HourlySalaryTable(salaryList);

        WorkTime hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0));

        assertThat(hourlySalaryTable.salaryOf(hourlyWorkTime), is(new UsdMoney(new BigDecimal("30"))));
    }
}
