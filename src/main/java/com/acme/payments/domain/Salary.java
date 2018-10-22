package com.acme.payments.domain;

import java.util.List;

public interface Salary {

    MonetaryAmount calculate(List<WorkEvent> workSchedule);
}
