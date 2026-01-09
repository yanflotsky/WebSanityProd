# WebSanityProd

A Java-based web automation testing framework using Playwright.

## 🔒 Security Features

**Encrypted Password Protection**: This project uses AES-256 encryption to secure sensitive credentials. Passwords are encrypted using your machine's unique identifier (USERNAME + COMPUTERNAME), ensuring they can only be decrypted on the machine where they were encrypted.

📖 **See [SECURE_PASSWORD_SETUP.md](SECURE_PASSWORD_SETUP.md) for password encryption instructions.**

## Project Structure

```
WebSanityProd/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── websanity/
│   │               └── BaseTest.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── websanity/
│       │           └── tests/
│       │               └── SampleTest.java
│       └── resources/
├── pom.xml
└── README.md
```

## Prerequisites

- Java JDK 17 or higher
- Maven 3.6 or higher

## Getting Started

### 1. Install Dependencies

```bash
mvn clean install
```

### 2. Install Playwright Browsers

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### 3. Run Tests

```bash
mvn test
```

## Features

- **Playwright**: Modern web automation framework
- **JUnit 5**: Latest testing framework
- **AssertJ**: Fluent assertion library
- **Maven**: Project management and build tool
- **Allure Report**: Beautiful and informative test reports

## 📊 Allure Report

This project is configured with Allure for generating beautiful test reports with graphs, timelines, and detailed step-by-step information.

### Quick Start with Allure

**Run tests and open report:**
```bash
run-tests-with-allure.bat
```

**Open existing report:**
```bash
open-allure-report.bat
```

**Manual commands:**
```bash
# Run tests
mvn clean test

# Generate and open report
mvn allure:serve
```

📖 **See [ALLURE_REPORT.md](ALLURE_REPORT.md) for detailed Allure instructions.**

## Configuration

The project is configured with:
- Java 17
- Playwright 1.48.0
- JUnit 5.10.1
- Chromium browser (default)
- Headless mode disabled for debugging
- Viewport size: 1920x1080

## Writing Tests

All test classes should extend `BaseTest` which provides:
- Playwright instance initialization
- Browser launching and closing
- Page context management
- Automatic cleanup after each test

### Example Test

```java
package com.websanity.tests;

import com.websanity.BaseTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MyTest extends BaseTest {
    
    @Test
    void testExample() {
        page.navigate("https://example.com");
        assertThat(page.title()).contains("Example");
    }
}
```

## Running Specific Tests

Run a specific test class:
```bash
mvn test -Dtest=SampleTest
```

Run a specific test method:
```bash
mvn test -Dtest=SampleTest#testGoogleSearch
```

## Browser Options

To run tests in headless mode, modify the `BaseTest.java`:

```java
browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
        .setHeadless(true));
```

## Additional Resources

- [Playwright Java Documentation](https://playwright.dev/java/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [AssertJ Documentation](https://assertj.github.io/doc/)

