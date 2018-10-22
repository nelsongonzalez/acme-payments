package com.acme.payments.domain.impl;

import com.acme.payments.domain.MonetaryAmount;
import com.acme.payments.domain.SalaryTable;
import com.acme.payments.domain.WorkEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public final class HourlySalaryTable implements SalaryTable {

    private final List<SalaryTable.Entry> salaries;

    public HourlySalaryTable(List<SalaryTable.Entry> salaryEntries) {
        Objects.requireNonNull(salaryEntries, "salaryEntries must be not null.");
        this.salaries = salaryEntries.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public MonetaryAmount workEventSalary(final WorkEvent workEvent) {
        Objects.requireNonNull(workEvent, "workEvent must be not null.");
        return salaries.stream()
                .filter((e) -> e.equalDayOfWeek(workEvent))
                .map((e) -> e.partialWorkEventSalary(workEvent))
                .reduce(MonetaryAmount::add)
                .orElse(Money.NOTHING);
    }

    public static class Entry implements SalaryTable.Entry {

        private static final int MINUTES_PER_HOUR = 60;

        private static final long NO_TIME = 0L;

        private final DayOfWeek dayOfWeek;

        private final LocalTime startTime;

        private final LocalTime endTime;

        private final MonetaryAmount monetaryAmount;

        public Entry(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, MonetaryAmount monetaryAmount) {
            Objects.requireNonNull(dayOfWeek, "dayOfWeek must be not null.");
            Objects.requireNonNull(startTime, "startTime must be not null.");
            Objects.requireNonNull(endTime, "endTime must be not null.");
            Objects.requireNonNull(monetaryAmount, "monetaryAmount must be not null.");
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
            this.monetaryAmount = monetaryAmount;
        }

        @Override
        public boolean equalDayOfWeek(WorkEvent workEvent) {
            return workEvent.equalDayOfWeek(dayOfWeek);
        }

        @Override
        public MonetaryAmount partialWorkEventSalary(WorkEvent workEvent) {
            Objects.requireNonNull(workEvent, "workEvent must be not null.");
            MonetaryAmount amount = new Money(BigDecimal.ZERO, monetaryAmount.getCurrency());
            if (equalDayOfWeek(workEvent)) {
                amount = calculateHourlySalary(workEvent);
            }
            return amount;
        }

        private MonetaryAmount calculateHourlySalary(WorkEvent workEvent) {
            MonetaryAmount accumulatedAmount = new Money(BigDecimal.ZERO, monetaryAmount.getCurrency());
            Duration workTimeDuration = workEvent.durationBetween(startTime, endTime);
            long minutes = workTimeDuration.toMinutes();
            if (minutes > NO_TIME) {
                long hours = workTimeDuration.toHours();
                accumulatedAmount = monetaryAmount.times(new BigDecimal(hours));
                accumulatedAmount = calculateFractionSalary(hours, minutes, accumulatedAmount);
            }
            return accumulatedAmount;
        }

        private MonetaryAmount calculateFractionSalary(long hours, long minutes, MonetaryAmount accumulatedAmount) {
            long remainingMinutes = minutes - (hours * MINUTES_PER_HOUR);
            if (remainingMinutes > NO_TIME) {
                BigDecimal fractionHour = new BigDecimal(remainingMinutes)
                        .divide(new BigDecimal(MINUTES_PER_HOUR), RoundingMode.HALF_UP);
                accumulatedAmount = accumulatedAmount.add(monetaryAmount.times(fractionHour));
            }
            return accumulatedAmount;
        }
    }

    public static class Builder {

        private List<SalaryTable.Entry> salaries;

        public Builder defaultSalaryTable() {
            var nineOClock = LocalTime.of(9, 0);
            var eighteenOClock = LocalTime.of(18, 0);
            var fifteenUsd = new Money(new BigDecimal("15"), Currency.getInstance("USD"));
            var twentyUsd = new Money(new BigDecimal("20"), Currency.getInstance("USD"));
            var twentyFiveUsd = new Money(new BigDecimal("25"), Currency.getInstance("USD"));
            var thirtyUsd = new Money(new BigDecimal("30"), Currency.getInstance("USD"));

            List<SalaryTable.Entry> salaryList = new ArrayList<>();
            for (DayOfWeek day : DayOfWeek.values()) {
                if (isWeekend(day)) {
                    addEntry(salaryList, day, LocalTime.MIDNIGHT, nineOClock, thirtyUsd);
                    addEntry(salaryList, day, nineOClock, eighteenOClock, twentyUsd);
                    addEntry(salaryList, day, eighteenOClock, LocalTime.MAX, twentyFiveUsd);
                } else {
                    addEntry(salaryList, day, LocalTime.MIDNIGHT, nineOClock, twentyFiveUsd);
                    addEntry(salaryList, day, nineOClock, eighteenOClock, fifteenUsd);
                    addEntry(salaryList, day, eighteenOClock, LocalTime.MAX, twentyUsd);
                }
            }

            this.salaries = Collections.unmodifiableList(salaryList);
            return this;
        }

        private void addEntry(List<SalaryTable.Entry> salaries,
                              DayOfWeek day, LocalTime from, LocalTime to, MonetaryAmount monetaryAmount) {
            salaries.add(new Entry(day, from, to, monetaryAmount));
        }

        private boolean isWeekend(DayOfWeek day) {
            return day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY);
        }

        public SalaryTable build() {
            return new HourlySalaryTable(salaries);
        }
    }
}
