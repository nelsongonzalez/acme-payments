package com.acme.payments.domain;

public interface Company {

    MonetaryAmount payTo(Employee employee);
}
