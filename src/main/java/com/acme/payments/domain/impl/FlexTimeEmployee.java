package com.acme.payments.domain.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.WorkEvent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FlexTimeEmployee implements Employee {

    private final List<WorkEvent> workSchedule;

    private final String name;

    public FlexTimeEmployee(String name, List<WorkEvent> workSchedule) {
        Objects.requireNonNull(name, "name must be not null.");
        Objects.requireNonNull(workSchedule, "workSchedule must be not null.");
        this.name = name;
        this.workSchedule = workSchedule.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<WorkEvent> getWorkSchedule() {
        return workSchedule;
    }
}
