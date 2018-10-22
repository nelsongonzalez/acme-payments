package com.acme.payments;

import com.acme.payments.view.impl.HourlyEmployeeSalaryModel;
import com.acme.payments.view.impl.SystemOutSalaryObserver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class Shell {

    private final String[] commandLineArguments;

    public Shell(String... commandLineArguments) {
        Objects.requireNonNull(commandLineArguments, "commandLineArguments must be not null.");
        this.commandLineArguments = commandLineArguments;
    }

    public void run() {
        var help = new Help();
        if (commandLineArguments.length == 1 && !"--help".equals(commandLineArguments[0])) {
            Path path = Path.of(commandLineArguments[0]);
            if (Files.exists(path)) {
                var observableSalary = new HourlyEmployeeSalaryModel(path);
                var salaryObserver = new SystemOutSalaryObserver();
                observableSalary.addObserver(salaryObserver);
                observableSalary.calculate();
                observableSalary.removeObserver(salaryObserver);
            } else {
                help.invalidArguments();
            }
        } else {
            help.usage();
        }
    }
}
