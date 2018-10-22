package com.acme.payments.view;

import com.acme.payments.application.EmployeeSchedule;
import com.acme.payments.view.impl.HourlyEmployeeSalaryModel;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.beans.PropertyChangeEvent;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class EmployeeSalaryModelTest {

    @Test
    public void shouldCalculateSalaries() {
        var pathAsString = EmployeeSchedule.class.getResource("/fileFiveLines.txt").getPath();
        var observableSalary = new HourlyEmployeeSalaryModel(Path.of(pathAsString));
        var salaryObserver = mock(SalaryObserver.class);
        observableSalary.addObserver(salaryObserver);

        observableSalary.calculate();

        var argument = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        verify(salaryObserver, times(5)).propertyChange(argument.capture());
        assertThat(argument.getValue().getNewValue(), is("The amount to pay RENE is: 215 USD"));
    }
}
