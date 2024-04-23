$data = Get-WmiObject MSAcpi_ThermalZoneTemperature -Namespace "root/wmi"
$temp=@()
$temp=$data.CurrentTemperature
$sensor=1
foreach($line in $temp){
    $k=$line/10
    $kelvin=[math]::round($k,2)
    $c=$kelvin-273.15
    $celsius=[math]::round($c,2)
    Write-Output $celsius
    $sensor++
}
