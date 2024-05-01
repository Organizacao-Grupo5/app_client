@echo off
powershell.exe -ExecutionPolicy Bypass -Command ^
$data = Get-WmiObject MSAcpi_ThermalZoneTemperature -Namespace "root/wmi"; ^
$temp = $data.CurrentTemperature; ^
foreach ($line in $temp) { ^
    $k = $line / 10; ^
    $kelvin = [math]::round($k, 2); ^
    $c = $kelvin - 273.15; ^
    $celsius = [math]::round($c, 2); ^
    Write-Output $celsius ^
}