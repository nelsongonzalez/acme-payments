package com.acme.payments.domain;

import java.math.BigDecimal;

public final class FlexTimeCompany implements Company {

    public FlexTimeCompany(String name) {
    }

    @Override
    public Money amountToPay(Employee employee) {
        return new UsdMoney(new BigDecimal("215"));
    }
}
