@echo off
setlocal

if "%~1"=="" (
    exit /b 1
)

set "PID=%~1"

:: Verifica se o processo com o PID fornecido existe
tasklist /fi "PID eq %PID%" 2>nul | find /i "%PID%" >nul
if %errorlevel% neq 0 (
    exit /b 1
)

:: Obtém a quantidade de RAM usada pelo processo em KB
for /f "tokens=5" %%a in ('tasklist /fi "PID eq %PID%" ^| find /i "%PID%"') do (
    set "RAM_KB=%%a"
)

:: Remove a letra 'K' e as vírgulas do valor de memória
set "RAM_KB=%RAM_KB:K=%"
set "RAM_KB=%RAM_KB:,=%"

:: Converte a RAM usada pelo processo para MB
set /a "RAM_MB=%RAM_KB% / 1024"

:: Exibe a RAM usada pelo processo em MB
echo %RAM_MB%

exit /b 0
