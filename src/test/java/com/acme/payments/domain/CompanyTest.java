package com.acme.payments.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class CompanyTest {

    @Test
    public void shouldCalcAmountOfMoney() {
        Company acmeCompany = new FlexTimeCompany("ACME");
        Employee reneEmployee = mock(Employee.class);

        MonetaryAmount payToRene = acmeCompany.amountToPay(reneEmployee);

        assertThat(payToRene, is(new Money(new BigDecimal("215"), Currency.getInstance("USD"))));
    }
}
