package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;

public interface MonetaryAmount {

    Currency getCurrency();

    BigDecimal getAmount();

    MonetaryAmount add(MonetaryAmount monetaryAmount);

    MonetaryAmount times(BigDecimal times);
}
