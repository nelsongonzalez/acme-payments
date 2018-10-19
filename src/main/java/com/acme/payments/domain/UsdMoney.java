package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class UsdMoney implements Money {

    public static final Money ZERO = new UsdMoney(BigDecimal.ZERO);

    private static final Currency CURRENCY = Currency.getInstance("USD");

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
        return amount.equals(usdMoney.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CURRENCY, amount);
    }

    @Override
    public Currency currency() {
        return CURRENCY;
    }

    @Override
    public BigDecimal amount() {
        return amount;
    }

    @Override
    public Money add(Money money) {
        if (!CURRENCY.equals(money.currency())) {
            throw new IllegalArgumentException(
                    String.format("Parameter's currency is different of %s", CURRENCY.getCurrencyCode()));
        }
        return new UsdMoney(amount.add(money.amount()));
    }
}
