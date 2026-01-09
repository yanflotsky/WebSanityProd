@echo off
echo ========================================
echo Running tests and generating Allure report
echo ========================================

echo.
echo Step 1: Cleaning previous test results...
call mvn clean

echo.
echo Step 2: Running tests...
call mvn test

echo.
echo Step 3: Generating Allure report...
call mvn allure:report

echo.
echo Step 4: Opening Allure report in browser...
call mvn allure:serve

pause

