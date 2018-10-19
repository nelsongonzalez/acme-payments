package com.acme.payments.domain;

public interface Company {

    MonetaryAmount amountToPay(Employee employee);
}
