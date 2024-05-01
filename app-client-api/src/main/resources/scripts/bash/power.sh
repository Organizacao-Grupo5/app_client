if [[ "$(uname -s)" == "Linux" ]]; then
    if command -v upower >/dev/null 2>&1; then
        battery_info=$(upower -i /org/freedesktop/UPower/devices/battery_BAT0)

        battery_percentage=$(echo "$battery_info" | grep "percentage" | awk '{print $2}')

        battery_percentage=${battery_percentage//[!0-9]/}

        echo $battery_percentage
    fi
fi
