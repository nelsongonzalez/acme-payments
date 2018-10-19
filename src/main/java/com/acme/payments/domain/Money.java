package com.acme.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;

public interface Money {

    Currency currency();

    BigDecimal amount();

    Money add(Money money);
}
