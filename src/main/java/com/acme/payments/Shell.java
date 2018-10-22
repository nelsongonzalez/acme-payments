package com.acme.payments;

import com.acme.payments.view.impl.HourlySalaryModelObservable;
import com.acme.payments.view.impl.OutputStreamSalaryObserver;

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
        if (commandLineArguments.length == 1) {
            Path path = Path.of(commandLineArguments[0]);
            if (Files.exists(path)) {
                var observableSalary = new HourlySalaryModelObservable(path);
                var salaryObserver = new OutputStreamSalaryObserver();
                observableSalary.addPropertyChangeListener(salaryObserver);
                observableSalary.calculate();
                observableSalary.removePropertyChangeListener(salaryObserver);
            } else {
                help.invalidArguments();
            }
        } else {
            help.usage();
        }
    }
}
