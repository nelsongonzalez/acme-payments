package com.acme.payments.domain.impl;

import com.acme.payments.domain.WorkTime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public final class HourlyWorkTime implements WorkTime {

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public HourlyWorkTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        Objects.requireNonNull(dayOfWeek, "dayOfWeek must be not null.");
        Objects.requireNonNull(startTime, "startTime must be not null.");
        Objects.requireNonNull(endTime, "endTime must be not null.");
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equalDayOfWeek(DayOfWeek otherDayOfWeek) {
        return dayOfWeek.equals(otherDayOfWeek);
    }

    @Override
    public Duration durationBetween(LocalTime limitStartTime, LocalTime limitEndTime) {
        Objects.requireNonNull(limitStartTime, "limitStartTime must be not null.");
        Objects.requireNonNull(limitEndTime, "limitEndTime must be not null.");
        Duration duration = Duration.ZERO;
        if (isBetween(startTime, limitStartTime, limitEndTime) || isBetween(endTime, limitStartTime, limitEndTime)) {
            duration = calculateDurationBetween(limitStartTime, limitEndTime);
        }
        return duration;
    }

    private boolean isBetween(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return time.isAfter(startTime) && time.isBefore(endTime);
    }

    private Duration calculateDurationBetween(LocalTime limitStartTime, LocalTime limitEndTime) {
        var startDuration = startTime;
        if (limitStartTime.isAfter(startTime)) {
            startDuration = limitStartTime;
        }
        var endDuration = endTime;
        if (limitEndTime.isBefore(endTime)) {
            endDuration = limitEndTime;
        }
        return Duration.between(startDuration, endDuration);
    }
}
