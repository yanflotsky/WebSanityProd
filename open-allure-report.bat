@echo off
echo ========================================
echo Opening existing Allure report
echo ========================================
echo.
echo Note: This will open the report from the last test run
echo If you want to run tests first, use run-tests-with-allure.bat
echo.
call mvn allure:serve
pause

