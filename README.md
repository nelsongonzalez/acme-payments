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

The design in based on object oriented principles, working by contracts,
immutability and  driven by tests.
