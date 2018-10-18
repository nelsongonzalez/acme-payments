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
    public void shouldGetSalary() {
        List<HourlySalaryTable.Entry> salaryList = List.of(
                new HourlySalaryTable.Entry(DayOfWeek.MONDAY, LocalTime.of(9, 1), LocalTime.of(18, 0), new UsdMoney(new BigDecimal("15")))
        );
        SalaryTable hourlySalaryTable = new HourlySalaryTable(salaryList);

        WorkTime hourlyWorkTime = new HourlyWorkTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0));

        assertThat(hourlySalaryTable.salaryOf(hourlyWorkTime), is(new UsdMoney(new BigDecimal("30"))));
    }
}
