@echo off
chcp 65001 > nul

echo ===============================================
echo               MONDAY Test Runner
echo ===============================================
echo.

echo [1/5] Checking Java version...
java -version

REM check if using Java 21
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVAVER=%%g
)

set JAVAVER=%JAVAVER:"=%
for /f "delims=. tokens=1" %%v in ("%JAVAVER%") do (
    set MAJORVER=%%v
)

if NOT "%MAJORVER%"=="21" (
    echo.
    echo =============== ERROR ===============
    echo Please use Java 21 ^(current: %MAJORVER%^)
    echo ===================================
    exit /b 1
)

echo [√] Java version check passed
echo.

echo [2/5] Setting up test environment...
if not exist ..\bin (
    mkdir ..\bin
    echo Created bin directory
)

if exist ACTUAL.TXT (
    del ACTUAL.TXT
    echo Cleaned up previous test outputs
)

echo.
echo [3/5] Compiling source files...
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\monday\*.java
IF ERRORLEVEL 1 (
    echo.
    echo =============== ERROR ===============
    echo         BUILD FAILURE
    echo ===================================
    exit /b 1
)

echo [√] Compilation successful
echo.

echo [4/5] Running test files...
echo.

REM Define test files in order
set TEST_FILES=01-happy-path.txt 02-empty-errors.txt 03-todo-errors.txt 04-deadline-errors.txt 05-event-errors.txt 06-mark-unmark-errors.txt 07-case-insensitivity.txt 08-edge-cases.txt
set PASSED=0
set FAILED=0

for %%f in (%TEST_FILES%) do (
    echo Testing: %%f
    java -classpath ..\bin monday.Monday < %%f > ACTUAL.TXT

    FC ACTUAL.TXT %%~nf-expected.txt > nul
    if ERRORLEVEL 1 (
        echo   [FAILED] %%f
        set /a FAILED+=1
    ) else (
        echo   [PASSED] %%f
        set /a PASSED+=1
    )
    echo.
)

echo [5/5] Test Summary
echo.
echo ===============================================
echo              Test Results
echo ===============================================
echo   Passed: %PASSED%
echo   Failed: %FAILED%
echo   Total: 8 test files
echo ===============================================
echo.

if %FAILED% GTR 0 (
    echo =============== ERROR ===============
    echo         Some tests FAILED
    echo ===================================
    exit /b 1
) else (
    echo ============= SUCCESS ==============
    echo      All tests passed!
    echo ===================================
    exit /b 0
)
