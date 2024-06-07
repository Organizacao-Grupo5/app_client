@echo off
setlocal

if "%~1"=="" (
    echo Nenhum PID fornecido.
    exit /b 1
)

set "PID=%~1"

tasklist /fi "PID eq %PID%" 2>nul | find /i "%PID%" >nul
if %errorlevel% neq 0 (
    echo Processo com o PID %PID% não encontrado.
    exit /b 1
)

for /f "tokens=5" %%a in ('tasklist /fi "PID eq %PID%" ^| find /i "%PID%"') do (
    set "RAM_KB=%%a"
)

set "RAM_KB=%RAM_KB:K=%"
set "RAM_KB=%RAM_KB:,=%"

set /a "RAM_MB=%RAM_KB% / 1024"

echo %RAM_MB%

exit /b 0
