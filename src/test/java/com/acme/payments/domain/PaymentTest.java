package com.acme.payments.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PaymentTest {

    @Test
    public void shoulGetZeroAmount() {
        List<WorkTime> workedTime = List.of();
        Payment weeklyPayment = new WeeklyPayment(workedTime);
        assertThat(weeklyPayment.amount(), is(new Money(BigDecimal.ZERO, Currency.getInstance("USD"))));
    }

    @Test
    public void shoulGetPaymentAmount() {
        List<WorkTime> workedTime = List.of(
                new HourlyWorkTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
                new HourlyWorkTime(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
                new HourlyWorkTime(DayOfWeek.THURSDAY, LocalTime.of(1, 0), LocalTime.of(3, 0)),
                new HourlyWorkTime(DayOfWeek.SATURDAY, LocalTime.of(14, 0), LocalTime.of(18, 0)),
                new HourlyWorkTime(DayOfWeek.SUNDAY, LocalTime.of(20, 0), LocalTime.of(21, 0))
        );
        Payment weeklyPayment = new WeeklyPayment(workedTime);

        assertThat(weeklyPayment.amount(), is(new Money(new BigDecimal("215"), Currency.getInstance("USD"))));
    }
}
