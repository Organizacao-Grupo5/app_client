if ! command -v sensors &> /dev/null; then
    sudo apt-get update
    sudo apt-get install -y lm-sensors
    sudo sensors-detect --auto
fi

sensor_data=$(sensors)

IFS=$'\n' read -d '' -r -a lines <<<"$sensor_data"

temperature_values=()
for line in "${lines[@]}"; do
    if [[ "$line" =~ (Tctl|Core) ]]; then
        temperature=$(echo "$line" | grep -o -E '[0-9]+\.[0-9]+')
        if [[ -n "$temperature" ]]; then
            temperature_values+=("$temperature")
        fi
    fi
done

if [ ${#temperature_values[@]} -eq 0 ]; then
    echo "Não foi possível encontrar a temperatura da CPU."
else
    for temp in "${temperature_values[@]}"; do
        echo "$temp"
    done
fi