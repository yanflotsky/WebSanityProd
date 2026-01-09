# Docker Parallel Testing Guide

## Overview

This project supports running tests in parallel Docker containers with automatic Allure report generation. The `isLocalRun` parameter in `pom.xml` controls the execution mode.

## Execution Modes

### Local Mode (`isLocalRun=true`)
- Tests run on your local machine
- Allure listener is **disabled**
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

## Quick Start

### Interactive Menu (Recommended)
```bash
run-tests-menu.bat
```

### Direct Commands

**Local execution:**
```bash
run-tests-local.bat
```

**Docker execution with report:**
```bash
run-tests-docker.bat
```

**Docker execution with automatic report opening:**
```bash
run-tests-docker-with-report.bat
```

**Check setup:**
```bash
test-setup.bat
```

## Configuration

### Setting isLocalRun in pom.xml
```xml
<properties>
    <isLocalRun>true</isLocalRun>   <!-- Local execution -->
    <!-- or -->
    <isLocalRun>false</isLocalRun>  <!-- Docker execution -->
</properties>
```

### Command Line Override
```bash
mvn test -DisLocalRun=true   # Local
mvn test -DisLocalRun=false  # Docker
```

## Docker Architecture

### Parallel Execution Flow
```
1. Build Docker image with tests
2. Launch two containers simultaneously:
   ├─> Container 1: AdminPortalSanityTest
   └─> Container 2: TeleadminSanityTest
3. Both containers run in parallel
4. Collect results from both containers
5. Merge results into target/allure-results/
6. Generate unified Allure report
```

### Container Resources
- Memory: 2GB per container
- CPU: 2 cores per container
- Network: Bridge (isolated)
- Volumes: Mounted for results

## Requirements

### Local Execution
- Java 21+
- Maven 3.9+
- Playwright browsers installed

### Docker Execution
- Docker Desktop installed and running
- Minimum 4GB RAM available (2GB × 2 containers)
- Minimum 4 CPU cores recommended
- ~2GB free disk space

## Results Structure

```
target/
├── allure-results/              # Merged results (Docker)
├── allure-results-admin/        # AdminPortal test results (Docker)
├── allure-results-teleadmin/    # Teleadmin test results (Docker)
├── allure-results-local/        # Local test results
└── site/allure-maven-plugin/    # Generated HTML report
```

## Docker Commands

### Build images
```bash
docker compose build
```

### Run tests
```bash
docker compose up --abort-on-container-exit
```

### View logs
```bash
docker compose logs admin-portal-tests
docker compose logs teleadmin-tests
```

### Stop containers
```bash
docker compose down
```

### Clean up everything
```bash
docker compose down --rmi all --volumes
```

## Environment Variables

Configure in `docker-compose.yml` or create a `.env` file:

```env
ENV_URL=https://secure.telemessage.com
TELEADMIN_USERNAME=yanteleadmin
TELEADMIN_PASSWORD=RApg4UKShrGdWIH4GQEUIg==
```

## Allure Reports

### Local Mode
Generate and view manually when needed:
```bash
mvn allure:serve target/allure-results-local
```

### Docker Mode
Allure reports are automatically generated after test completion.

**View the report:**
```bash
mvn allure:serve target/allure-results
```

**Generate static report:**
```bash
mvn allure:report
# Report location: target/site/allure-maven-plugin/
```


## Troubleshooting

### Docker containers won't start
- Ensure Docker Desktop is running
- Check available resources (RAM/CPU)
- Verify Docker installation: `docker --version`

### Build is slow
- First build downloads dependencies and Playwright browsers
- Subsequent builds use cache and are faster
- Rebuild without cache: `docker compose build --no-cache`

### Tests fail in Docker
- Check container logs: `docker compose logs`
- Verify environment variables
- Ensure sufficient resources allocated

### Allure report not generated
- Check results exist: `dir target\allure-results`
- Generate manually: `mvn allure:report`
- Verify Allure plugin in pom.xml

### IDE warning "Cannot resolve symbol 'isLocalRun'"
- This is an IDE caching issue
- Maven handles the property correctly
- Safe to ignore or invalidate IDE cache

## CI/CD Integration

GitHub Actions workflow included at `.github/workflows/docker-tests.yml`

Triggers:
- Push to main/develop branches
- Pull requests
- Manual dispatch

The workflow:
1. Builds Docker images
2. Runs tests in parallel containers
3. Merges results
4. Generates Allure report
5. Publishes to GitHub Pages

## Best Practices

### Use Local Mode When:
- Developing new tests
- Debugging test failures
- Quick verification of changes
- Don't need a formal report

### Use Docker Mode When:
- Running full test suite
- Need comprehensive Allure report
- Running in CI/CD pipeline
- Want parallel execution (faster)
- Need consistent environment

## Maven Profiles

The project uses Maven profiles to control Allure listener:

**Local Profile** (activated when `isLocalRun=true`)
- Disables Allure listener
- Faster execution
- No report overhead

**Docker Profile** (activated when `isLocalRun=false`)
- Enables Allure listener
- Collects detailed test data
- Generates comprehensive reports

## Performance

### Sequential vs Parallel
- **Local (sequential)**: Tests run one after another
- **Docker (parallel)**: Tests run simultaneously
- **Time saved**: Up to 50% faster with parallel execution

### Resource Usage
- Each container uses 2GB RAM
- Total requirement: 4GB+ RAM
- 2 CPU cores per container recommended

## Tips

1. Use `test-setup.bat` to verify all dependencies before running tests
2. Use `run-tests-menu.bat` for easy access to all options
3. Check logs if tests fail: `docker compose logs`
4. Local mode is faster for development iterations
5. Docker mode ensures consistent test environment across machines

## Support

For issues or questions:
1. Check this guide's Troubleshooting section
2. Review container logs: `docker compose logs`
3. Verify setup: `test-setup.bat`
4. Check Docker Desktop settings and resources

