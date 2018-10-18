package com.acme.payments.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public final class HourlySalaryTable implements SalaryTable {

    private final List<Entry> salaryList;

    public HourlySalaryTable(List<HourlySalaryTable.Entry> salaryList) {
        this.salaryList = salaryList;
    }

    @Override
    public Money salaryOf(WorkTime workTime) {
        return null;
    }

    public static class Entry {
        private final DayOfWeek dayOfWeek;
        private final LocalTime from;
        private final LocalTime to;
        private final Money salary;

        public Entry(DayOfWeek dayOfWeek, LocalTime from, LocalTime to, Money salary) {
            this.dayOfWeek = dayOfWeek;
            this.from = from;
            this.to = to;
            this.salary = salary;
        }
    }
}
