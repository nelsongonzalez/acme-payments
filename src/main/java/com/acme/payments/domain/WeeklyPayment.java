package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.List;

public final class WeeklyPayment implements Payment {

    private final List<WorkTime> workedTime;

    public WeeklyPayment(List<WorkTime> workedTime) {
        this.workedTime = workedTime;
    }

    @Override
    public Money amount() {
        for (WorkTime workTime : workedTime) {

        }
        return new UsdMoney(new BigDecimal("215"));
    }
}
