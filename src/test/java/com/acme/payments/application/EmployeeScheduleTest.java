package com.acme.payments.application;

import com.acme.payments.domain.Employee;
import com.acme.payments.domain.WorkTime;
import com.acme.payments.application.impl.DataFileEmployeeSchedule;
import com.acme.payments.application.impl.MinLinesEmployeeSchedule;
import org.junit.Test;

import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class EmployeeScheduleTest {

    @Test
    public void shouldReadEmployeeFromFile() {
        String pathAsString = EmployeeSchedule.class.getResource("/fileOk.txt").getPath();
        var employeeSchedule = new DataFileEmployeeSchedule(Path.of(pathAsString));
        var employees = employeeSchedule.read();

        Employee reneEmployee = employees.stream()
                .filter((e) -> "RENE".equals(e.getName()))
                .findFirst()
                .orElseThrow();
        List<WorkTime> reneWorkedTime = reneEmployee.getWorkTimes();
        assertThat(employees, hasSize(5));
        assertThat(reneEmployee.getWorkTimes(), hasSize(5));
        assertThat(reneWorkedTime, hasSize(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateMinimunLines() {
        String pathAsString = EmployeeSchedule.class.getResource("/fileTwoLines.txt").getPath();
        var employeeSchedule = new MinLinesEmployeeSchedule(new DataFileEmployeeSchedule(Path.of(pathAsString)), 5);
        employeeSchedule.read();
        fail("Should thrown an exception");
    }
}
