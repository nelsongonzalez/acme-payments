# Acme Payments

## Build & Run

To build and run the project you need to have **JDK 11** and **Git client**.

When you have fulfilled these requirements please follow the next steps:

```
git clone https://github.com/nelsongonzalez/acme-payments.git
cd acme-payments/
./gradlew build
java -jar build/libs/acme-payments-1.0-SNAPSHOT.jar src/test/resources/fileOk.txt
```

To see more details about usage yo can use `--help` option:

```
java -jar build/libs/acme-payments-1.0-SNAPSHOT.jar --help
```

## Design

The design in based on object oriented principles and working by contracts,
immutable objects and driven by tests.

The package `com.acme.payments.view` implements an observable-observer design pattern
to allow using other view and show the salary.

Package `com.acme.payments.domain` contains contracts and implementations of the business (calculate salary).

Finally, `com.acme.payments.application` deals with the comunication between `view` and `domain`
packages.

For example, `view` receives a *file path* with schedules of employees, `application`
reads the file and creates instances of *employee* with the *work schedule* and finally calls the `domain`
that calculate de *salary* of the *employee*.

**Class diagram**

![UML Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/nelsongonzalez/acme-payments/master/classes.puml&fmt=svg)

**Components diagram**

![UML Components Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/nelsongonzalez/acme-payments/master/components.puml&fmt=svg)

## TODO

- [ ] Put domain sourcode in another module to allow better dependency management.
- [ ] Test with more shcedules.
