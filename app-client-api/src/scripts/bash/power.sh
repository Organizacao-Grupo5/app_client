#!/bin/bash

# Verifica se estamos em um sistema Linux
if [[ "$(uname -s)" == "Linux" ]]; then
    # Usa o upower para obter informações da bateria
    if command -v upower >/dev/null 2>&1; then
        # Obtem detalhes da bateria
        battery_info=$(upower -i /org/freedesktop/UPower/devices/battery_BAT0)

        # Extrai a porcentagem da bateria
        battery_percentage=$(echo "$battery_info" | grep "percentage" | awk '{print $2}')

        echo "Porcentagem da bateria (Linux): $battery_percentage"
    fi
fi
