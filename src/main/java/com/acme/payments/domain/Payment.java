package com.acme.payments.domain;

import java.util.List;

public interface Payment {

    MonetaryAmount totalSalary(List<WorkTime> workTimes);
}
