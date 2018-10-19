package com.acme.payments.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public final class HourlyWorkTime implements WorkTime {

    private final DayOfWeek dayOfWeek;
    private final LocalTime from;
    private final LocalTime to;

    public HourlyWorkTime(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        this.dayOfWeek = dayOfWeek;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean fallsIn(LocalTime localTime) {
        return from.plusMinutes(1).equals(localTime) || from.isAfter(localTime);
    }

    @Override
    public long hours() {
        return Duration.between(from, to).toHours();
    }
}
