@echo off
setlocal enabledelayedexpansion

REM Navigate to project directory
cd /d "%~dp0"

REM Download dependencies if not already present
if not exist "target\libs" (
    echo Downloading JavaFX and other dependencies (this may take a minute)...
    call mvn dependency:copy-dependencies -DoutputDirectory=target/libs -q
    if errorlevel 1 (
        echo Error downloading dependencies. Make sure Maven is installed.
        pause
        exit /b 1
    )
    echo Dependencies downloaded successfully!
)

REM Build classpath by iterating through all jars
set "CLASSPATH=target\classes"
for %%f in (target\libs\*.jar) do (
    set "CLASSPATH=!CLASSPATH!;%%f"
)

REM Run the application
echo.
echo =====================================
echo Starting Library Management System...
echo =====================================
echo.

java -cp "%CLASSPATH%" com.library.App

if errorlevel 1 (
    echo.
    echo Error: Application failed to start.
    echo Please ensure Java 21 or higher is installed.
    pause
)
