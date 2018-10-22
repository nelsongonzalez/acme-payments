package com.acme.payments.view;

import java.beans.PropertyChangeListener;

public interface SalaryObserver extends PropertyChangeListener {

    void displaySalary(String value);
}
