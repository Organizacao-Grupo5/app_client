@echo off
setlocal

if "%~1"=="" (
    echo Uso: %0 PID_do_processo
    exit /b 1
)

set "PID=%~1"

tasklist /fi "PID eq %PID%" 2>nul | find /i "%PID%" >nul
if %errorlevel% neq 0 (
    echo Processo com o PID %PID% não encontrado.
    exit /b 1
)

for /f "tokens=5" %%a in ('tasklist /fi "PID eq %PID%" ^| find /i "%PID%"') do (
    set "RAM=%%a"
)

echo %RAM%

exit /b 0
