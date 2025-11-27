@echo off
setlocal enabledelayedexpansion

REM First, copy dependencies if needed
if not exist "target\libs" (
    echo Downloading dependencies...
    call mvn dependency:copy-dependencies -DoutputDirectory=target/libs -q
)

REM Get all jar files from target\libs
set "CLASSPATH=target\classes"
for %%f in (target\libs\*.jar) do (
    set "CLASSPATH=!CLASSPATH!;%%f"
)

REM Run the application
java -cp "%CLASSPATH%" com.library.App
pause
