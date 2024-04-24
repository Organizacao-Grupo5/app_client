if ! command -v sensors &> /dev/null; then
    echo "O comando 'sensors' não está instalado."
    echo "Instalando 'lm-sensors'..."
    sudo apt-get update
    sudo apt-get install -y lm-sensors
    echo "Instalação concluída. Configurando sensores..."
    sudo sensors-detect --auto
fi

sensor_data=$(sensors)

IFS=$'\n' read -d '' -r -a lines <<<"$sensor_data"

temperature_lines=()
for line in "${lines[@]}"; do
    if [[ "$line" =~ (Tctl|Core) ]]; then
        temperature_lines+=("$line")
    fi
done

if [ ${#temperature_lines[@]} -eq 0 ]; then
    echo "Não foi possível encontrar a temperatura da CPU."
else
    echo "Temperatura(s) da CPU:"
    for line in "${temperature_lines[@]}"; do
        temperature=$(echo "$line" | grep -o -E '[0-9]+\.[0-9]+°C')
        if [[ -n "$temperature" ]]; then
            echo "$line: $temperature"
        else
            echo "$line (não foi possível extrair temperatura)"
        fi
    done
fi