package com.acme.payments.domain.impl;

import com.acme.payments.domain.WorkEvent;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public final class HourlyWorkEvent implements WorkEvent {

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public HourlyWorkEvent(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        Objects.requireNonNull(dayOfWeek, "dayOfWeek must be not null.");
        Objects.requireNonNull(startTime, "startTime must be not null.");
        Objects.requireNonNull(endTime, "endTime must be not null.");
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = LocalTime.MIDNIGHT.equals(endTime) ? LocalTime.MAX : endTime;
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
        if (isBetween(startTime, limitStartTime, limitEndTime)
                || isBetween(endTime, limitStartTime, limitEndTime)
                || isBetween(limitStartTime, startTime, endTime)
                || isBetween(limitEndTime, startTime, endTime)) {
            duration = calculateDurationBetween(limitStartTime, limitEndTime);
        }
        return duration;
    }

    private boolean isBetween(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return (time.equals(startTime) || time.isAfter(startTime)) && time.isBefore(endTime);
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
