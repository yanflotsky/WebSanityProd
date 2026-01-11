# WebSanityProd

E2E automation testing framework for TeleMessage platform using Playwright, Java 21, and Docker with parallel execution capabilities.

---

## 📑 Table of Contents

- [🏗️ Project Structure](#️-project-structure)
- [⚙️ Execution Modes](#️-execution-modes)
- [🎯 Centralized Test Configuration](#-centralized-test-configuration)
- [📸 Automatic Screenshots](#-automatic-screenshots)
- [🔐 Encrypted Password Management](#-encrypted-password-management)
- [📧 Gmail API for MFA](#-gmail-api-for-mfa)
- [🚀 Quick Start](#-quick-start)
- [📊 Test Suites](#-test-suites)
- [🔧 Configuration](#-configuration)
- [📦 Docker Details](#-docker-details)
- [🧪 Page Object Model](#-page-object-model)
- [🔍 Key Features](#-key-features)
- [📈 Allure Reporting](#-allure-reporting)
- [🛠️ Troubleshooting](#️-troubleshooting)
- [📁 Important Files](#-important-files)
- [🔒 Security Notes](#-security-notes)
- [📝 Adding New Tests](#-adding-new-tests)
- [🎯 CI/CD Integration](#-cicd-integration)
- [📞 Support](#-support)
- [🚀 Batch Scripts](#-batch-scripts)
- [🔧 Advanced Configuration](#-advanced-configuration)
- [🐳 Docker Commands](#-docker-commands)
- [🔍 Running Specific Tests](#-running-specific-tests)
- [📚 Resources](#-resources)

---

## 🏗️ Project Structure

```
WebSanityProd/
├── src/
│   ├── main/java/com/websanity/
│   │   ├── adminPortalPages/          # Admin Portal page objects
│   │   ├── teleadminPages/            # Teleadmin page objects
│   │   ├── enums/                     # Enumerations (UserTypes, Country, etc.)
│   │   ├── models/                    # Data models (UserParams)
│   │   └── BasePage.java              # Base page with common methods
│   │
│   └── test/java/com/websanity/
│       ├── tests/
│       │   ├── AdminPortalSanityTest.java    # Admin Portal test suite
│       │   ├── TeleadminSanityTest.java      # Teleadmin test suite
│       │   └── TestsRunner.java              # Centralized test runner
│       ├── AdminPortalBaseTest.java   # Admin Portal base with auto-login
│       ├── BaseTest.java              # Base test with browser setup
│       └── EncryptionUtil.java        # Password encryption utility
│
├── secrets/                           # Encrypted credentials (gitignored)
├── target/
│   ├── allure-results/               # Combined Allure results
│   ├── allure-results-admin/         # Admin Portal results (Docker)
│   ├── allure-results-teleadmin/     # Teleadmin results (Docker)
│   └── screenshots/                  # Auto-captured on test failure
│
├── docker-compose.yml                # Parallel execution config
├── Dockerfile                        # Test container image
└── pom.xml                           # Maven dependencies
```

## ⚙️ Execution Modes

### 🖥️ Local Execution (`isLocalRun=true`)
- **Sequential execution** - tests run one after another
- **Headed browser** - visible browser window with slowMo
- **Fast feedback** - no Docker overhead
- **Single Allure report** - `target/allure-results/`

```bash
mvn clean test -DisLocalRun=true
```

### 🐳 Docker Execution (`isLocalRun=false`)
- **Parallel execution** - 2 test classes in separate containers
- **Headless browser** - no UI, optimized for CI/CD
- **Isolated environments** - no test interference
- **Separate reports** - merged into single Allure report

```bash
docker compose up --build
```

#### Container Configuration:
```yaml
services:
  admin-portal-tests:     # Container 1
    - Runs: AdminPortalSanityTest
    - Results: target/allure-results-admin/
    - Resources: 2 CPU, 2GB RAM
    
  teleadmin-tests:        # Container 2
    - Runs: TeleadminSanityTest
    - Results: target/allure-results-teleadmin/
    - Resources: 2 CPU, 2GB RAM
```

**Both containers run simultaneously**, cutting execution time in half.

## 🎯 Centralized Test Configuration

Add new test classes in **one place** (`TestsRunner.java`):

```java
private static final String[] TEST_CLASSES = {
    "AdminPortalSanityTest:admin-portal-tests:allure-results-admin",
    "TeleadminSanityTest:teleadmin-tests:allure-results-teleadmin",
    // "NewTest:new-service:allure-results-new"  // Add here
};
```

This automatically updates:
- ✅ Local Maven execution
- ✅ Docker container checks
- ✅ Allure results cleanup
- ✅ Results merging

## 📸 Automatic Screenshots

Screenshots are captured **after every test** (pass or fail):

**Location:** `target/screenshots/`
**Format:** `ClassName_TestName_YYYY-MM-DD_HH-mm-ss.png`
**Works in:** Local (headed) **AND** Docker (headless) ✅

**Implementation:** `BaseTest.java`
```java
@AfterEach
void takeScreenshotOnFailure(TestInfo testInfo) {
    // Captures full-page screenshot
    // Attaches to Allure report
    // Works in headless Docker containers
}
```

Screenshots are automatically attached to Allure reports.

## 🔐 Encrypted Password Management

### Secret Files (`secrets/` directory)
```
secrets/
├── teleadmin_password.txt    # Teleadmin login password
└── gmail_password.txt         # Gmail App Password for MFA
```

**⚠️ Never commit secrets to Git** - directory is gitignored

### Docker Secrets Integration
```yaml
secrets:
  teleadmin_password:
    file: ./secrets/teleadmin_password.txt
  gmail_password:
    file: ./secrets/gmail_password.txt
```

Secrets are mounted at `/run/secrets/` in Docker containers.

### Loading Secrets in Code
```java
// Auto-detects Docker vs Local mode
String password = USE_DOCKER_SECRETS 
    ? Files.readString(Path.of("/run/secrets/teleadmin_password"))
    : Files.readString(Path.of("secrets/teleadmin_password.txt"));
```

## 📧 Gmail API for MFA Code Retrieval

**Auto-retrieves MFA codes** from email during Admin Portal login.

### Setup Gmail App Password:
1. Enable 2FA on Gmail account
2. Generate App Password: https://myaccount.google.com/apppasswords
3. Save to `secrets/gmail_password.txt`

### How It Works:
```java
// AdminPortalLogInPage.java
public AdminPortalMenuPage loginToAdminPortalWithAutoUser() {
    // 1. Enter username/password
    // 2. Select Email MFA option
    // 3. Connect to Gmail via IMAP
    // 4. Search for new MFA email (last 10 seconds)
    // 5. Extract 6-digit code
    // 6. Auto-fill and submit
    // 7. Handle post-login popups
}
```

**Configuration:**
- Email: `yanflotskysmarsh@gmail.com` (configured in test)
- Protocol: IMAP over SSL (imap.gmail.com:993)
- Search: Only emails received after timestamp
- Timeout: 30 attempts with 2s delay (60s total)

## 🚀 Quick Start

### 1. Setup Secrets
```bash
# Create secrets directory
mkdir secrets

# Add passwords (plain text)
echo "your_teleadmin_password" > secrets/teleadmin_password.txt
echo "your_gmail_app_password" > secrets/gmail_password.txt
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Run Tests

#### Option A: Local (Fast Development)
```bash
mvn clean test -DisLocalRun=true
```
- Sequential execution
- Headed browser (visible)
- Results: `target/allure-results/`

#### Option B: Docker (Parallel CI/CD)
```bash
docker compose up --build
```
- Parallel execution (2 containers)
- Headless browser
- Results: Merged into `target/allure-results/`

### 4. View Allure Report
```bash
mvn allure:serve
```
Opens interactive HTML report with:
- Test results, durations, trends
- Screenshots attached to each test
- Detailed logs and stack traces

## 📊 Test Suites

### AdminPortalSanityTest (Container 1)
E2E tests for Admin Portal functionality:
- User management (add, update, suspend/activate, delete)
- Message composition and delivery verification
- Contact management
- Status verification

### TeleadminSanityTest (Container 2)
E2E tests for Teleadmin Portal functionality:
- User registration and account management
- Company settings and configurations
- Archive management (sources, destinations, plans)
- Application-level settings (WhatsApp, Telegram, Signal, Android)
- User signatures and capture settings

**Tests use `@Order` annotation** for sequential execution within each suite.

**Current Coverage:** 20+ automated E2E scenarios

## 🔧 Configuration

### Switch Execution Mode
Edit `pom.xml`:
```xml
<properties>
    <isLocalRun>true</isLocalRun>  <!-- Local mode -->
    <!-- <isLocalRun>false</isLocalRun>  Docker mode -->
</properties>
```

Or via command line:
```bash
mvn test -DisLocalRun=false  # Docker mode
mvn test -DisLocalRun=true   # Local mode
```

### Browser Configuration
`BaseTest.java`:
```java
boolean headless = isLocalRun.equals("false"); // Docker = headless

BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
    .setHeadless(headless)
    .setSlowMo(headless ? 0 : 50);  // SlowMo only in local mode

if (headless) {
    options.setArgs(Arrays.asList(
        "--disable-dev-shm-usage",
        "--disable-gpu",
        "--no-sandbox"
    ));
}
```

## 📦 Docker Details

### Resource Limits
```yaml
deploy:
  resources:
    limits:
      memory: 2g
      cpus: '2'
    reservations:
      memory: 1g
      cpus: '1'
```

### Volume Mapping
```yaml
volumes:
  - ./target/allure-results-admin:/app/target/allure-results
  - ./target/allure-results-teleadmin:/app/target/allure-results
```
Results are automatically copied to host machine.

### Network Isolation
```yaml
networks:
  test-network:
    driver: bridge
```
Containers can communicate but are isolated from host network.

## 🧪 Page Object Model

### Base Classes
```java
BasePage                  // Common page methods (wait, click, fill)
  ├── BaseTest            // Browser lifecycle, screenshots
  └── AdminPortalBaseTest // Auto-login with MFA
```

### Page Objects
```java
// Admin Portal
AdminPortalLogInPage      // MFA login, Gmail integration
AdminPortalMenuPage       // Navigation
AdminPortalUserManagementPage
AdminPortalComposeMessagePage
AdminPortalSentItemsPage
AdminPortalMyContactsPage

// Teleadmin
TeleadminLogInPage
TeleadminMenuPage
TeleadminFindUsersPage
TeleadminUpdateUserPage
TeleadminSignUpPage
```

### Fluent API Pattern
```java
userManagementPage
    .clickUserManagement()
    .fillSearchInp(username)
    .clickSearchBtn()
    .clickFirstUserCheckbox()
    .clickDeleteBtn()
    .clickConfirmYesBtn();
```

## 🔍 Key Features

### 1. Auto-Login with Session Reuse
```java
@BeforeAll
static void setupAndLoginOnce() {
    // Login performed ONCE before all tests
    // Session kept open for all tests in class
}
```
Saves 30+ seconds per test run.

### 2. MFA Auto-Handling
- Email MFA: Auto-retrieves code from Gmail
- Popup dismissal: Auto-closes post-login popups
- Retry logic: 30 attempts with exponential backoff

### 3. Dynamic Waits
```java
private void waitForLoadingToDisappear() {
    loadingOverlay.waitFor(new Locator.WaitForOptions()
        .setState(WaitForSelectorState.HIDDEN)
        .setTimeout(10000));
}
```

### 4. Screenshot on Failure
- Captured automatically after each test
- Full-page screenshots (`.setFullPage(true)`)
- Works in headless Docker
- Attached to Allure report

### 5. Centralized Test Runner
- Single configuration point for all tests
- Dynamic Maven command generation
- Automatic Docker container management
- Results merging from multiple sources

## 📈 Allure Reporting

### Generate & Serve Report
```bash
mvn allure:serve
```

### Report Contents
- **Overview**: Pass/fail statistics, duration trends
- **Suites**: Test organization by Epic/Feature/Story
- **Graphs**: Timeline, categories, severity distribution
- **Behaviors**: BDD-style feature breakdown
- **Attachments**: Screenshots, logs, traces

### Allure Annotations
```java
@Epic("Admin Portal")
@Feature("User Management")
@Story("Add User")
@Severity(SeverityLevel.CRITICAL)
@Description("Create new Pro User and verify in table")
```

## 🛠️ Troubleshooting

### Docker Issues
```bash
# Clean up containers
docker compose down
docker system prune -a

# Rebuild from scratch
docker compose build --no-cache
docker compose up
```

### Playwright Issues
```bash
# Reinstall browsers
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --force"
```

### Test Failures
1. Check `target/screenshots/` for visual state
2. Review Allure report for detailed logs
3. Enable debug logging: `-Dplaywright.debug=1`

## 📁 Important Files

| File | Purpose |
|------|---------|
| `TestsRunner.java` | Centralized test configuration |
| `BaseTest.java` | Browser setup, screenshot capture |
| `AdminPortalBaseTest.java` | Auto-login with MFA |
| `docker-compose.yml` | Parallel execution config |
| `pom.xml` | Maven dependencies, execution mode |
| `secrets/*.txt` | Encrypted credentials (gitignored) |

## 🔒 Security Notes

- ✅ Secrets are **gitignored** (never committed)
- ✅ Docker secrets mounted read-only
- ✅ Gmail App Password (not real password)
- ✅ No hardcoded credentials in code

## 📝 Adding New Tests

### 1. Create Test Class
```java
@Epic("New Feature")
public class NewFeatureTest extends AdminPortalBaseTest {
    @Test
    @Order(1)
    void newTest() {
        // Test implementation
    }
}
```

### 2. Add to TestsRunner
```java
private static final String[] TEST_CLASSES = {
    "AdminPortalSanityTest:admin-portal-tests:allure-results-admin",
    "TeleadminSanityTest:teleadmin-tests:allure-results-teleadmin",
    "NewFeatureTest:new-feature-tests:allure-results-new"  // Add here
};
```

### 3. Update docker-compose.yml
```yaml
new-feature-tests:
  build: .
  command: mvn test -Dtest=NewFeatureTest -DisLocalRun=false
  volumes:
    - ./target/allure-results-new:/app/target/allure-results
```

Done! Test will run in parallel with others.

## 🎯 CI/CD Integration

```yaml
# GitHub Actions / Jenkins
steps:
  - name: Run Tests
    run: docker compose up --build
    
  - name: Generate Allure Report
    run: mvn allure:report
    
  - name: Publish Report
    uses: actions/upload-artifact@v3
    with:
      name: allure-report
      path: target/allure-report/
```

## 📞 Support

- **Project Structure**: Check `CENTRALIZED_CONFIG.md`
- **Screenshots**: Check `SCREENSHOTS.md`
- **Docker Guide**: Check `DOCKER_GUIDE.md`

---

**Tech Stack:** Java 21 | Playwright | Maven | Docker | Allure | JUnit 5 | Lombok | SLF4J

**Execution Time:** ~2-3 minutes (parallel) | ~4-6 minutes (sequential)

**Test Coverage:** E2E scenarios across Admin Portal & Teleadmin platforms

## 🚀 Batch Scripts

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

## 🔧 Advanced Configuration

### Switch Execution Mode via pom.xml

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

## 🐳 Docker Commands

```bash
# Build images
docker compose build

# Run tests in parallel
docker compose up --build

# View logs
docker compose logs admin-portal-tests
docker compose logs teleadmin-tests

# Stop containers
docker compose down

# Clean everything
docker compose down --rmi all --volumes
```

## 🔍 Running Specific Tests

```bash
# Run specific test class
mvn test -Dtest=AdminPortalSanityTest

# Run specific test method
mvn test -Dtest=AdminPortalSanityTest#addProUser

# Run with local mode
mvn test -Dtest=AdminPortalSanityTest -DisLocalRun=true
```

## 📚 Resources

- [Playwright Java Documentation](https://playwright.dev/java/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Allure Framework](https://docs.qameta.io/allure/)
- [Docker Documentation](https://docs.docker.com/)

---

**Tech Stack:** Java 21 | Playwright | Maven | Docker | Allure | JUnit 5 | Lombok | SLF4J

**Execution Time:** ~2-3 minutes (parallel) | ~4-6 minutes (sequential)

**Test Coverage:** E2E scenarios across Admin Portal & Teleadmin platforms
