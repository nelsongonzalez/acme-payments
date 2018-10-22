package com.acme.payments.view.impl;

import com.acme.payments.view.SalaryObserver;

import java.beans.PropertyChangeEvent;

public final class OutputStreamSalaryObserver implements SalaryObserver {

    private static final String WHITE_BOLD = "\033[1;37m";

    private static final String RESET = "\033[0m";

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        displaySalary((String) event.getNewValue());
    }

    @Override
    public void displaySalary(String value) {
        printColored(value);
    }

    private void printColored(String value) {
        System.out.println(String.format("%s%s%s", WHITE_BOLD, value, RESET));
    }

}
