$batteryInfo = Get-WmiObject -Class Win32_Battery | Select-Object EstimatedChargeRemaining
$batteryPercentage = $batteryInfo.EstimatedChargeRemaining
Write-Output $batteryPercentage