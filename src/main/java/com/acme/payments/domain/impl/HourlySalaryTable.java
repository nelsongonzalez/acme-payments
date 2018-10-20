package com.acme.payments.domain.impl;

import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.WorkTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

public final class HourlySalaryTable implements SalaryTable {

    private final List<SalaryTable.Entry> salaries;

    public HourlySalaryTable(List<SalaryTable.Entry> salaries) {
        this.salaries = salaries;
    }

    @Override
    public MonetaryAmount workTimeSalary(final WorkTime workTime) {
        // TODO parameterize currency
        return salaries.stream()
                .map((e) -> e.partialWorkTimeSalary(workTime))
                .reduce(MonetaryAmount::add)
                .orElse(Money.NOTHING);
    }

    public static class Entry implements SalaryTable.Entry {

        private static final int MINUTES_PER_HOUR = 60;

        private final DayOfWeek dayOfWeek;

        private final LocalTime startTime;

        private final LocalTime endTime;

        private final MonetaryAmount salary;

        public Entry(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, MonetaryAmount salary) {
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
            this.salary = salary;
        }

        @Override
        public MonetaryAmount partialWorkTimeSalary(WorkTime workTime) {
            if (!workTime.equalDayOfWeek(dayOfWeek)) {
                return new Money(BigDecimal.ZERO, salary.getCurrency());
            }

            MonetaryAmount amount = new Money(BigDecimal.ZERO, salary.getCurrency());

            Duration workTimeDuration = workTime.limitDuration(startTime, endTime);
            long minutes = workTimeDuration.toMinutes();
            if (minutes > 0) {
                long hours = workTimeDuration.toHours();
                amount = salary.times(hours);

                long remainingMinutes = minutes - (hours * MINUTES_PER_HOUR);
                if (remainingMinutes > 0) {
                    BigDecimal fractionHour = new BigDecimal(remainingMinutes).divide(new BigDecimal(MINUTES_PER_HOUR), RoundingMode.HALF_UP);
                    amount = amount.add(salary.times(fractionHour));
                }
            }
            return amount;
        }
    }
}
