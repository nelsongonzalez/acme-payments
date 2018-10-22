package com.acme.payments.application.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.WorkEvent;
import com.acme.payments.domain.impl.FlexTimeEmployee;
import com.acme.payments.domain.impl.HourlyWorkEvent;
import com.acme.payments.application.EmployeeSchedule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class DataFileEmployeeSchedule implements EmployeeSchedule {

    private static final Pattern SCHEDULE_REGEX
            = Pattern.compile("(\\w+)=(((MO|TU|WE|TH|FR|SA|SU)(\\d{2}:\\d{2}-\\d{2}:\\d{2}),)*(MO|TU|WE|TH|FR|SA|SU)(\\d{2}:\\d{2}-\\d{2}:\\d{2}))");

    private static final Pattern SCHEDULE_ENTRY_REGEX
            = Pattern.compile("(MO|TU|WE|TH|FR|SA|SU)(\\d{2}:\\d{2})-(\\d{2}:\\d{2})");

    private static final Pattern SPLIT_SCHEDULE_ENTRIES_REGEX
            = Pattern.compile(",");

    final Path path;

    public DataFileEmployeeSchedule(Path path) {
        Objects.requireNonNull(path, "path must be not null.");
        this.path = path;
    }

    @Override
    public List<Employee> scheduling() {
        try {
            return Files.lines(path)
                    .map(this::readExecutive)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            throw new IllegalArgumentException("File data is invalid.");
        }
    }

    private FlexTimeEmployee readExecutive(String executiveAsString) {
        var matcher = SCHEDULE_REGEX.matcher(executiveAsString);
        if (matcher.matches()) {
            var name = matcher.group(1);
            var schedule = matcher.group(2);
            var workTimes = SPLIT_SCHEDULE_ENTRIES_REGEX.splitAsStream(schedule)
                    .map(this::readSchedule)
                    .collect(Collectors.toUnmodifiableList());
            return new FlexTimeEmployee(name, workTimes);
        }
        throw new IllegalArgumentException(String.format("Invalid line '%s'.", executiveAsString));
    }

    private WorkEvent readSchedule(String scheduleAsString) {
        var matcher = SCHEDULE_ENTRY_REGEX.matcher(scheduleAsString);
        if (matcher.matches()) {
            var day = matcher.group(1);
            var dayOfWeek = readDayOfWeek(day);
            var from = matcher.group(2);
            var to = matcher.group(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
            return new HourlyWorkEvent(dayOfWeek, LocalTime.parse(from, formatter), LocalTime.parse(to, formatter));
        }
        throw new IllegalArgumentException(String.format("The value '%s' is an invalid schedule entry.", scheduleAsString));
    }

    private DayOfWeek readDayOfWeek(String dayAsString) {
        DayOfWeek dayOfWeek;
        switch (dayAsString) {
            case "MO":
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case "TU":
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case "WE":
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case "TH":
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case "FR":
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case "SA":
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case "SU":
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
            default:
                throw new IllegalArgumentException(String.format("The value '%s' is an invalid day abbreviation.", dayAsString));
        }
        return dayOfWeek;
    }
}
