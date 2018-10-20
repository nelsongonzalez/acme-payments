package com.acme.payments.domain.impl;

import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.WorkTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class HourlySalaryTable implements SalaryTable {

    private final List<SalaryTable.Entry> salaries;

    public HourlySalaryTable(List<SalaryTable.Entry> salaries) {
        Objects.requireNonNull(salaries, "salaries must be not null.");
        this.salaries = salaries.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public MonetaryAmount workTimeSalary(final WorkTime workTime) {
        Objects.requireNonNull(workTime, "workTime must be not null.");
        return salaries.stream()
                .map((e) -> e.partialWorkTimeSalary(workTime))
                .reduce(MonetaryAmount::add)
                .orElse(Money.NOTHING);
    }

    public static class Entry implements SalaryTable.Entry {

        private static final int MINUTES_PER_HOUR = 60;

        private static final long NO_TIME = 0L;

        private final DayOfWeek dayOfWeek;

        private final LocalTime startTime;

        private final LocalTime endTime;

        private final MonetaryAmount salary;

        public Entry(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, MonetaryAmount salary) {
            Objects.requireNonNull(dayOfWeek, "dayOfWeek must be not null.");
            Objects.requireNonNull(startTime, "startTime must be not null.");
            Objects.requireNonNull(endTime, "endTime must be not null.");
            Objects.requireNonNull(salary, "salary must be not null.");
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
            this.salary = salary;
        }

        @Override
        public MonetaryAmount partialWorkTimeSalary(WorkTime workTime) {
            Objects.requireNonNull(workTime, "workTime must be not null.");
            MonetaryAmount amount = new Money(BigDecimal.ZERO, salary.getCurrency());
            if (workTime.equalDayOfWeek(dayOfWeek)) {
                amount = calculateHourlySalary(workTime);
            }
            return amount;
        }

        private MonetaryAmount calculateHourlySalary(WorkTime workTime) {
            MonetaryAmount accumulatedAmount = new Money(BigDecimal.ZERO, salary.getCurrency());
            Duration workTimeDuration = workTime.durationBetween(startTime, endTime);
            long minutes = workTimeDuration.toMinutes();
            if (minutes > NO_TIME) {
                long hours = workTimeDuration.toHours();
                accumulatedAmount = salary.times(new BigDecimal(hours));
                accumulatedAmount = calculateFractionSalary(hours, minutes, accumulatedAmount);
            }
            return accumulatedAmount;
        }

        private MonetaryAmount calculateFractionSalary(long hours, long minutes, MonetaryAmount accumulatedAmount) {
            long remainingMinutes = minutes - (hours * MINUTES_PER_HOUR);
            if (remainingMinutes > NO_TIME) {
                BigDecimal fractionHour = new BigDecimal(remainingMinutes)
                        .divide(new BigDecimal(MINUTES_PER_HOUR), RoundingMode.HALF_UP);
                accumulatedAmount = accumulatedAmount.add(salary.times(fractionHour));
            }
            return accumulatedAmount;
        }
    }
}
