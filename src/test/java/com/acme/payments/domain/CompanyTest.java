package com.acme.payments.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class CompanyTest {

    @Test
    public void shouldCalcAmountOfMoney() {
        Company acmeCompany = new FlexTimeCompany("ACME");
        Employee reneEmployee = mock(Employee.class);

        Money payToRene = acmeCompany.amountToPay(reneEmployee);

        assertThat(payToRene, is(new UsdMoney(new BigDecimal("215"))));
    }
}
