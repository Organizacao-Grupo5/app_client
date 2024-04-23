#$data = Get-WmiObject MSAcpi_ThermalZoneTemperature -Namespace "root/wmi"
#$temp=@()
#$temp=$data.CurrentTemperature
#$sensor=1
#foreach($line in $temp){
#    $k=$line/10
#    $kelvin=[math]::round($k,2)
#    $c=$kelvin-273.15
#    $celsius=[math]::round($c,2)
#    Write-Output $celsius
#    $sensor++
#}


# Captura de temperatura de Cpu de Linux
$sensorsCheck = Get-Command sensors -ErrorAction SilentlyContinue

#opcional
if (-not $sensorsCheck) {
    Write-Output "O comando 'sensors' não está instalado."
    # Instala o pacote 'lm-sensors' se necessário
    Start-Process -NoNewWindow -Wait -FilePath "sudo" -ArgumentList "apt-get install -y lm-sensors"
}

$sensorData = sensors

# divide a saída do comando em linhas
$lines = $sensorData -split "`n"

# filtra por linhas que contenham 'Tctl'
$cpuTemperatureLines = $lines | Where-Object { $_ -match "Tctl" }

if ($cpuTemperatureLines.Count -eq 0) {
    Write-Output "Não foi possível encontrar a temperatura da CPU."
} else {

    #extrai a temperatura em Celsius do 'Tctl'
    foreach ($line in $cpuTemperatureLines) {
        $matches = [regex]::Matches($line, "\d+(\.\d+)?°C")
        foreach ($match in $matches) {
            Write-Output "Temperatura da CPU: $match (Linux)"
        }
    }
}
