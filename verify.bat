@echo off
REM Local check: compile the plugin and run the unit tests.
REM No Minecraft server is started.
REM
REM Requirements: Maven 3.9+ and a JDK 21 on PATH
REM Usage: verify.bat

cd /d "%~dp0"

echo ==^> Compiling and running tests (mvn clean verify)
call mvn -B -ntp clean verify
if errorlevel 1 (
    echo.
    echo ==^> Build or tests FAILED.
    exit /b 1
)

echo.
echo ==^> Done. Build compiled and all tests passed.
