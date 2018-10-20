package com.acme.payments.domain.impl;

import com.acme.payments.domain.WorkTime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public final class HourlyWorkTime implements WorkTime {

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public HourlyWorkTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private boolean between(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return time.isAfter(startTime) && time.isBefore(endTime);
    }

    @Override
    public Duration limitDuration(LocalTime limitStartTime, LocalTime limitEndTime) {
        if (between(startTime, limitStartTime, limitEndTime)
                || between(endTime, limitStartTime, limitEndTime)) {

            var startDuration = startTime;
            if (limitStartTime.compareTo(startTime) > 0) {
                startDuration = limitStartTime;
            }

            var endDuration = endTime;
            if (limitEndTime.compareTo(endTime) < 0) {
                endDuration = limitEndTime;
            }

            return Duration.between(startDuration, endDuration);
        }
        return Duration.ZERO;
    }

    @Override
    public boolean equalDayOfWeek(DayOfWeek otherDayOfWeek) {
        return dayOfWeek.equals(otherDayOfWeek);
    }
}
