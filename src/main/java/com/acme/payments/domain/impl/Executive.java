package com.acme.payments.domain.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.WorkTime;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Executive implements Employee {

    private final List<WorkTime> workedTime;

    public Executive(List<WorkTime> workedTime) {
        Objects.requireNonNull(workedTime, "workedTime must be not null.");
        this.workedTime = workedTime.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<WorkTime> getWorkedTime() {
        return workedTime;
    }
}
