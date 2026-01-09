# WebSanityProd

A Java-based web automation testing framework using Playwright with Docker support for parallel test execution.

## Overview

This framework provides two execution modes:
- **Local Mode**: Run tests on your machine for quick development
- **Docker Mode**: Run tests in parallel containers with automatic Allure reporting

## Prerequisites

### For Local Execution
- Java JDK 21 or higher
- Maven 3.9 or higher
- Playwright browsers installed

### For Docker Execution
- Docker Desktop installed and running
- 4GB+ RAM available
- Docker Compose support

## Quick Start

### 1. Install Dependencies

```bash
mvn clean install
```

### 2. Install Playwright Browsers

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### 3. Run Tests

#### Interactive Menu (Recommended)
```bash
run-tests-menu.bat
```

This opens a menu with options for:
- Local execution (no Allure report)
- Docker parallel execution (with Allure report)
- Build Docker images
- Clean test results

#### Direct Commands

**Local Execution:**
```bash
run-tests-local.bat
```

**Docker Execution:**
```bash
run-tests-docker.bat
```

**Docker Execution with Auto-Open Report:**
```bash
run-tests-docker-with-report.bat
```

**Check Setup:**
```bash
test-setup.bat
```

## Execution Modes

### Local Mode (`isLocalRun=true`)
- Tests run on your local machine
- Allure listener is **disabled** (no report overhead)
- Results saved to `target/allure-results-local/`
- No automatic report generation
- Best for development and debugging

### Docker Mode (`isLocalRun=false`)
- Tests run in **parallel Docker containers**
- Two containers execute simultaneously:
  - `admin-portal-tests` → AdminPortalSanityTest
  - `teleadmin-tests` → TeleadminSanityTest
- Allure listener is **enabled**
- Results automatically merged from both containers
- Automatic Allure report generation
- Best for full test runs and CI/CD

### Switching Between Modes

Edit `pom.xml` and change the `isLocalRun` value:

```xml
<properties>
    <isLocalRun>true</isLocalRun>   <!-- Local: Run on your machine -->
    <!-- or -->
    <isLocalRun>false</isLocalRun>  <!-- Docker: Run in parallel containers -->
</properties>
```

After changing the value, simply run tests from your IDE as usual. The framework will automatically:
- Use local execution if `isLocalRun=true`
- Use Docker parallel execution if `isLocalRun=false`

**Optional: Override via command line**
```bash
mvn test -DisLocalRun=true   # Force local mode
mvn test -DisLocalRun=false  # Force Docker mode
```

## Docker Parallel Execution

The Docker mode runs **AdminPortalSanityTest** and **TeleadminSanityTest** in parallel, reducing total execution time.

### Features
- ✅ Parallel execution in isolated containers
- ✅ Automatic result merging
- ✅ Unified Allure report generation
- ✅ Resource control (2GB RAM, 2 CPU per container)
- ✅ Consistent test environment

### How It Works
```
1. Build Docker image with tests
2. Launch two containers simultaneously
3. Each container runs one test class
4. Results collected in separate volumes
5. Results merged into target/allure-results/
6. Allure report generated automatically
```

📖 **See [DOCKER_GUIDE.md](DOCKER_GUIDE.md) for detailed Docker instructions.**

## Project Structure

```
WebSanityProd/
├── src/
│   ├── main/java/com/websanity/
│   │   ├── BaseTest.java
│   │   ├── AdminPortalBaseTest.java
│   │   └── ...
│   └── test/java/com/websanity/tests/
│       ├── AdminPortalSanityTest.java
│       └── TeleadminSanityTest.java
├── target/
│   ├── allure-results/              # Merged results (Docker)
│   ├── allure-results-admin/        # AdminPortal results (Docker)
│   ├── allure-results-teleadmin/    # Teleadmin results (Docker)
│   └── allure-results-local/        # Local results
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## Features

- **Playwright**: Modern browser automation
- **JUnit 5**: Latest testing framework
- **AssertJ**: Fluent assertions
- **Maven**: Project management
- **Allure**: Beautiful test reports
- **Docker**: Parallel execution support
- **Lombok**: Reduced boilerplate code

## Allure Reports

### Generate Report Manually
```bash
mvn allure:report
```

### View Report
```bash
# Open existing report
open-allure-report.bat

# Or serve results directory
allure serve target/allure-results

# Or serve local results
allure serve target/allure-results-local
```

### Docker Mode
Reports are generated automatically after test execution in `target/site/allure-maven-plugin/`.

## Configuration

### Security
This project uses AES-256 encryption for credentials. Passwords are encrypted using your machine's unique identifier (USERNAME + COMPUTERNAME), ensuring they can only be decrypted on the same machine.

### Browser Settings
The framework is configured with:
- Java 21
- Playwright 1.57.0
- JUnit 5.10.1
- Chromium browser (default)
- Headless mode disabled (for debugging)
- Viewport: 1920x1080

## Writing Tests

Extend `BaseTest` or `AdminPortalBaseTest` depending on your needs:

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

```bash
# Run specific test class
mvn test -Dtest=AdminPortalSanityTest

# Run specific test method
mvn test -Dtest=AdminPortalSanityTest#addProUser

# Run with local mode
mvn test -Dtest=AdminPortalSanityTest -DisLocalRun=true
```

## Docker Commands

```bash
# Build images
docker compose build

# Run tests
docker compose up --abort-on-container-exit

# View logs
docker compose logs admin-portal-tests
docker compose logs teleadmin-tests

# Stop containers
docker compose down

# Clean everything
docker compose down --rmi all --volumes
```

## CI/CD Integration

A GitHub Actions workflow is included at `.github/workflows/docker-tests.yml` that:
- Builds Docker images
- Runs tests in parallel containers
- Generates and publishes Allure reports
- Uploads test artifacts

## Troubleshooting

### Docker Issues
- Ensure Docker Desktop is running: `docker ps`
- Check available resources (4GB+ RAM needed)
- Rebuild images: `docker compose build --no-cache`

### Test Failures
- Check logs: `docker compose logs`
- Verify environment variables in `docker-compose.yml`
- Ensure Docker containers have sufficient resources

### Allure Report Issues
- Verify results exist: `dir target\allure-results` (Windows) or `ls target/allure-results` (Linux/Mac)
- Generate manually: `mvn allure:report`
- View report: `mvn allure:serve target/allure-results`

## Resources

- [Playwright Java Documentation](https://playwright.dev/java/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Allure Framework](https://docs.qameta.io/allure/)
- [Docker Documentation](https://docs.docker.com/)

