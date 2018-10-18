package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class UsdMoney implements Money {

    public static final Money ZERO = new UsdMoney(BigDecimal.ZERO);

    private final Currency currency = Currency.getInstance("USD");

    private final BigDecimal amount;

    public UsdMoney(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UsdMoney usdMoney = (UsdMoney) other;
        return currency.equals(usdMoney.currency) && amount.equals(usdMoney.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
