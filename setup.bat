@echo off
echo ========================================
echo WebSanityProd - Project Setup
echo ========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo.
    echo Please install Maven:
    echo 1. Download from: https://maven.apache.org/download.cgi
    echo 2. Extract to C:\Program Files\Apache\maven
    echo 3. Add to PATH: C:\Program Files\Apache\maven\bin
    echo 4. Restart your terminal
    echo.
    pause
    exit /b 1
)

echo Maven found!
call mvn --version
echo.

echo Step 1: Installing dependencies...
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Dependency installation failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Installing Playwright browsers...
echo This may take a few minutes on first run...
call mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"

echo.
echo ========================================
echo Setup completed successfully!
echo ========================================
echo.
echo You can now:
echo - Open the project in IntelliJ IDEA
echo - Run tests with: mvn test
echo - Or use: run-tests.bat
echo.
pause

