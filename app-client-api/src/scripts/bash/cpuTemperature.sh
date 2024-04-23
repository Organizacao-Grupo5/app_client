#!/bin/bash

# Verifica se o comando 'sensors' está disponível
if ! command -v sensors &> /dev/null; then
    echo "O comando 'sensors' não está instalado."
    echo "Instalando 'lm-sensors'..."
    # Instala o pacote 'lm-sensors'
    sudo apt-get update
    sudo apt-get install -y lm-sensors
    echo "Instalação concluída. Configurando sensores..."
    sudo sensors-detect --auto
fi

# Captura a saída do comando 'sensors'
sensor_data=$(sensors)

# Divide a saída em linhas
IFS=$'\n' read -d '' -r -a lines <<<"$sensor_data"

# Procura por linhas que contenham "Tctl" ou "Core" para extrair temperaturas
temperature_lines=()
for line in "${lines[@]}"; do
    if [[ "$line" =~ (Tctl|Core) ]]; then
        temperature_lines+=("$line")
    fi
done

# Se não encontrou linhas com temperatura, exibe mensagem de erro
if [ ${#temperature_lines[@]} -eq 0 ]; then
    echo "Não foi possível encontrar a temperatura da CPU."
else
    echo "Temperatura(s) da CPU:"
    for line in "${temperature_lines[@]}"; do
        # Extrai o valor da temperatura usando regex
        temperature=$(echo "$line" | grep -o -E '[0-9]+\.[0-9]+°C')
        if [[ -n "$temperature" ]]; then
            echo "$line: $temperature"
        else
            echo "$line (não foi possível extrair temperatura)"
        fi
    done
fi
