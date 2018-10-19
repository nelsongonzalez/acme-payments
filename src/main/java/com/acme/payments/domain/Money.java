package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class Money implements MonetaryAmount {

    private final BigDecimal amount;

    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Money money = (Money) other;
        return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public MonetaryAmount add(MonetaryAmount monetaryAmount) {
        if (!currency.equals(monetaryAmount.getCurrency())) {
            throw new IllegalArgumentException(
                    String.format("Parameter's currency is different of %s", currency.getCurrencyCode()));
        }
        return new Money(amount.add(monetaryAmount.getAmount()), currency);
    }
}
