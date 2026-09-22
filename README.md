# Library Lending App

A small library book-lending service, used as a training sandbox for
**AI-assisted repository investigation with a Filesystem MCP server**.

Members borrow books and return them; late returns are fined.

## Requirements

- JDK 11 or newer — check with `java -version`
- Maven 3.8+ — check with `mvn -version`

If `mvn` is not found, install it first (macOS: `brew install maven`; or use
SDKMAN: `sdk install maven`). Maven needs a JDK on your `PATH` / `JAVA_HOME`.

## Build, run, and test (from a terminal)

Run these from the project root — the folder that contains `pom.xml`:

```bash
# Build: compile, run the tests, and package a jar into target/
mvn clean package

# Run the application (prints a few lending scenarios, writes logs/app.log)
mvn -q compile exec:java

# Run only the unit tests
mvn test
```

To build without running the tests, add `-DskipTests`
(for example `mvn clean package -DskipTests`).

The first build needs internet access so Maven can download its dependencies
(including JUnit) into your local `~/.m2` cache.

## What's in here

```
src/main/java/com/rsl/library
├── App.java            # entry point – runs a few lending scenarios
├── model/              # Book, Member, Loan, MembershipTier, LoanStatus
├── repository/         # in-memory stores
├── service/            # the business logic
└── util/               # logging setup
```

> Note: this README is intentionally light. Working out how the pieces fit
> together is the point of the investigation.
