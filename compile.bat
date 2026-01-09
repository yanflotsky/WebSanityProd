@echo off
echo Compiling project...

REM Try to find Maven in common locations
set MAVEN_CMD=

REM Check if mvn is in PATH
where mvn >nul 2>nul
if %errorlevel% equ 0 (
    set MAVEN_CMD=mvn
    goto :compile
)

REM Check IntelliJ bundled Maven
if exist "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.4\plugins\maven\lib\maven3\bin\mvn.cmd" (
    set "MAVEN_CMD=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.4\plugins\maven\lib\maven3\bin\mvn.cmd"
    goto :compile
)

echo ERROR: Maven not found!
exit /b 1

:compile
echo Using Maven: %MAVEN_CMD%
call "%MAVEN_CMD%" clean compile
exit /b %errorlevel%

