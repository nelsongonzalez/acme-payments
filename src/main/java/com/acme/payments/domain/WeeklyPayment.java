package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public final class WeeklyPayment implements Payment {

    private final List<WorkTime> workedTime;

    public WeeklyPayment(List<WorkTime> workedTime) {
        this.workedTime = workedTime;
    }

    @Override
    public MonetaryAmount amount() {
        for (WorkTime workTime : workedTime) {
        }
        // TODO parameterize currency
        return new Money(BigDecimal.ZERO, Currency.getInstance("USD"));
    }
}
