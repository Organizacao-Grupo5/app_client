if ($env:OS -like "*Windows_NT*") {
    $batteryInfo = Get-WmiObject -Class Win32_Battery | Select-Object EstimatedChargeRemaining, EstimatedRunTime
    $batteryPercentage = $batteryInfo.EstimatedChargeRemaining
    Write-Output  $batteryPercentage
} else {
    $result = upower -i /org/freedesktop/UPower/devices/battery_BAT0

    if ($?) {
        $lines = $result -split "\n"

        foreach ($line in $lines) {
            if ($line -like '*percentage:*') {
                $batteryPercentage = ($line -split ':')[1].Trim()
                Write-Output $batteryPercentage
            }
        }
    } else {
        Write-Output "O comando 'upower' não está disponível. Verifique se está instalado."
    }
}
