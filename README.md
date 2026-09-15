# President og Boms – Project Group 14 (TDT4100)

A JavaFX implementation of the Norwegian card game **President og Boms**, built as the semester project for TDT4100 at NTNU.

## Prerequisites

- **JDK 11 or newer** (the Maven compiler plugin targets Java `release 25`, so make sure your local JDK actually supports that release — install a recent JDK, e.g. JDK 21+/25, if `mvn compile` complains about an unsupported release)
- **Apache Maven 3.6+**
- A JavaFX-capable environment — Maven pulls in the correct native JavaFX binaries automatically via the `org.openjfx` dependencies, so no separate JavaFX SDK install is required

Check your setup with:

```bash
java -version
mvn -version
```

## Project structure

The actual Maven project lives in the `project/` subfolder, not the repository root:

```
project/
├── pom.xml                  # Main Maven build file
├── pom_with_maven_fx.xml    # Alternate pom (JavaFX + TestFX UI-testing setup)
└── src/
    ├── main/java/president/ # Application source (Main.java is the entry point)
    ├── main/resources/      # FXML views
    └── test/java/president/ # JUnit 5 tests
```

All Maven commands below must be run from inside the `project/` directory.

```bash
cd project
```

## Dependencies

Declared in `pom.xml` and resolved automatically by Maven — no manual downloads needed:

| Dependency | Version | Purpose |
|---|---|---|
| `org.openjfx:javafx-fxml` | 16-ea+5 | JavaFX UI toolkit + FXML support |
| `org.junit.jupiter:junit-jupiter-engine` | 5.7.0 | Unit testing (JUnit 5) |

Build plugins used:

| Plugin | Version | Purpose |
|---|---|---|
| `maven-compiler-plugin` | 3.8.0 | Compiles Java sources |
| `maven-surefire-plugin` | 3.0.0-M5 | Runs unit tests |
| `maven-failsafe-plugin` | 3.0.0-M5 | Runs integration tests (`--enable-preview`) |
| `org.openjfx:javafx-maven-plugin` | 0.0.5 | Runs the JavaFX app directly with `mvn javafx:run` |

> There is also a `pom_with_maven_fx.xml` file that additionally pulls in `org.testfx:testfx-core` and `org.testfx:testfx-junit5` (4.0.16-alpha) for automated JavaFX UI testing. Use it with `mvn -f pom_with_maven_fx.xml <goal>` if you need those.

## Running the project

From the `project/` directory:

1. **Install dependencies / compile:**
   ```bash
   mvn install
   ```

2. **Run the application** (launches the JavaFX GUI):
   ```bash
   mvn javafx:run
   ```

3. **Run the tests:**
   ```bash
   mvn test
   ```

## Importing into an IDE

- **Eclipse:** Import as an existing Maven project, pointing at the `project/` folder (it contains the `pom.xml`).
