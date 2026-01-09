@echo off
echo ========================================
echo WebSanityProd - Playwright Test Runner
echo ========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

echo Step 1: Cleaning and compiling project...
call mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Installing Playwright browsers...
call mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
if %errorlevel% neq 0 (
    echo WARNING: Browser installation may have issues
)

echo.
echo Step 3: Running tests...
call mvn test
if %errorlevel% neq 0 (
    echo Tests completed with failures
) else (
    echo All tests passed!
)

echo.
echo ========================================
echo Test execution completed
echo ========================================
pause

