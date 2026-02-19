@echo off
setlocal

rem Get JAVA_HOME_X64 from Machine scope first, then User scope
for /f "tokens=2,*" %%a in ('reg query "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v JAVA_HOME_X64 2^>nul ^| findstr /R /C:"\<JAVA_HOME_X64\>"') do (
    if /I "%%a"=="REG_SZ" set "JAVA_HOME_X64=%%b"
)
if not defined JAVA_HOME_X64 (
    for /f "tokens=2,*" %%a in ('reg query "HKCU\Environment" /v JAVA_HOME_X64 2^>nul ^| findstr /R /C:"\<JAVA_HOME_X64\>"') do (
        if /I "%%a"=="REG_SZ" set "JAVA_HOME_X64=%%b"
    )
)

rem Set JAVA_HOME and update PATH
set "JAVA_HOME=%JAVA_HOME_X64%"
set "PATH=%JAVA_HOME%\bin;%PATH%"

rem Display Java settings and run the application
java -XshowSettings:properties -version 2>&1 | findstr /C:"java.home" /C:"os.arch"
.\gradlew.bat run

endlocal
