package com.acme.payments.view;

import java.beans.PropertyChangeListener;

public interface Observable {

    void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);

    void removePropertyChangeListener(PropertyChangeListener propertyChangeListener);
}
