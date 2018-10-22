package com.acme.payments.domain.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.WorkTime;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Executive implements Employee {

    private final List<WorkTime> workTimes;

    private final String name;

    public Executive(String name, List<WorkTime> workTimes) {
        Objects.requireNonNull(name, "name must be not null.");
        Objects.requireNonNull(workTimes, "workTimes must be not null.");
        this.name = name;
        this.workTimes = workTimes.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<WorkTime> getWorkTimes() {
        return workTimes;
    }
}
