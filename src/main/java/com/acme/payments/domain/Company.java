package com.acme.payments.domain;

public interface Company {

    Money amountToPay(Employee employee);
}
