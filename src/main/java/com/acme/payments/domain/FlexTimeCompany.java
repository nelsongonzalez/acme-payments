package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;

public final class FlexTimeCompany implements Company {

    public FlexTimeCompany(String name) {
    }

    @Override
    public MonetaryAmount amountToPay(Employee employee) {
        // TODO parameterize currency
        return new Money(new BigDecimal("215"), Currency.getInstance("USD"));
    }
}
