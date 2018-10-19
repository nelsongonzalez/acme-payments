package com.acme.payments.domain;

import java.time.LocalTime;

public interface WorkTime {

    boolean fallsIn(LocalTime localTime);

    long hours();
}
