package com.acme.payments.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public interface WorkTime {

    Duration limitDuration(LocalTime startTime, LocalTime limitEndTime);

    boolean equalDayOfWeek(DayOfWeek dayOfWeek);
}
