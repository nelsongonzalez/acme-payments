package com.acme.payments.domain.impl;

import com.acme.payments.domain.MonetaryAmount;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class Money implements MonetaryAmount {

    public static final MonetaryAmount NOTHING = new NullMoney();

    private final BigDecimal amount;

    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        Objects.requireNonNull(amount, "amount must be not null.");
        Objects.requireNonNull(currency, "currency must be not null.");
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
        var money = (Money) other;
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
        Objects.requireNonNull(monetaryAmount, "monetaryAmount must be not null.");
        if (!currency.equals(monetaryAmount.getCurrency())) {
            throw new IllegalArgumentException(
                    String.format("Parameter's currency is different of %s", currency.getCurrencyCode()));
        }
        return new Money(amount.add(monetaryAmount.getAmount()), currency);
    }

    @Override
    public MonetaryAmount times(BigDecimal times) {
        Objects.requireNonNull(times, "times must be not null.");
        return new Money(amount.multiply(times), currency);
    }

    @Override
    public String toString() {
        return String.format("%,f %s ", amount, currency.getCurrencyCode());
    }

    private static class NullMoney implements MonetaryAmount {

        @Override
        public Currency getCurrency() {
            throw new UnsupportedOperationException();
        }

        @Override
        public BigDecimal getAmount() {
            throw new UnsupportedOperationException();
        }

        @Override
        public MonetaryAmount add(MonetaryAmount monetaryAmount) {
            throw new UnsupportedOperationException();
        }

        @Override
        public MonetaryAmount times(BigDecimal times) {
            throw new UnsupportedOperationException();
        }
    }
}
