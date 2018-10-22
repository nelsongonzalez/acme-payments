package com.acme.payments.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public interface WorkEvent {

    boolean equalDayOfWeek(DayOfWeek dayOfWeek);

    Duration durationBetween(LocalTime startTime, LocalTime limitEndTime);
}
