package com.acme.payments.domain.impl;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.WorkTime;

import java.util.List;

public final class Executive implements Employee {

    private final List<WorkTime> workedTime;

    public Executive(List<WorkTime> workedTime) {
        this.workedTime = workedTime;
    }

    @Override
    public List<WorkTime> getWorkedTime() {
        return workedTime;
    }
}
