#!/bin/bash

if [[ "$(uname -s)" == "Linux" ]]; then
    if command -v upower >/dev/null 2>&1; then
        battery_info=$(upower -i /org/freedesktop/UPower/devices/battery_BAT0)
        battery_percentage=$(echo "$battery_info" | grep "percentage" | awk '{print $2}')
        battery_percentage=${battery_percentage//[!0-9]/}
        echo $battery_percentage
    else
        echo "upower não encontrado"
        exit 1
    fi
else
    echo "Não é um sistema Linux"
    exit 1
fi
