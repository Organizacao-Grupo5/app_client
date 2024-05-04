if [ -z "$1" ]; then
    echo "Por favor, forneça um parâmetro."
    exit 1
fi

ps | grep "$1" | awk '{print $5}'