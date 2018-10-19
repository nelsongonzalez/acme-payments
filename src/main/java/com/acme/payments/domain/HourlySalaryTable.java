package com.acme.payments.domain;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public final class HourlySalaryTable implements SalaryTable {

    private final List<SalaryTable.Entry> salaries;

    public HourlySalaryTable(List<SalaryTable.Entry> salaries) {
        this.salaries = salaries;
    }

    @Override
    public Money salaryOf(WorkTime workTime) {
        Money salary = UsdMoney.ZERO;
        for (SalaryTable.Entry salaryEntry : salaries) {
            long hours = salaryEntry.hoursOf(workTime);
            salary = salary.add(salaryEntry.salaryBy(hours));
        }
        return salary;
    }

    public static class Entry implements SalaryTable.Entry {

        private final DayOfWeek dayOfWeek;

        private final LocalTime from;

        private final LocalTime to;

        private final Duration duration;

        private final Money salary;

        public Entry(DayOfWeek dayOfWeek, LocalTime from, LocalTime to, Money salary) {
            this.dayOfWeek = dayOfWeek;
            this.from = from;
            this.to = to;
            this.duration = Duration.between(from, to);
            this.salary = salary;
        }

        @Override
        public long hoursOf(WorkTime workTime) {
            if (workTime.fallsIn(from)) {
                return workTime.hours();
            }
            return 0L;
        }

        @Override
        public Money salaryBy(long hours) {
            return new UsdMoney(salary.amount().multiply(new BigDecimal(hours)));
        }
    }
}
