#!/bin/bash

echo "Atualizando o sistema..."
sudo apt-get update -y
sudo apt-get upgrade -y

echo "Verificando se o Docker está instalado..."
if ! command -v docker &> /dev/null
then
    echo "Docker não está instalado. Instalando Docker..."
    sudo apt-get install \
        ca-certificates \
        curl \
        gnupg \
        lsb-release -y

    sudo mkdir -p /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

    echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
    $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

    sudo apt-get update -y
    sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y

    sudo usermod -aG docker $USER
else
    echo "Docker já está instalado."
fi

echo "Verificando a versão do Docker..."
sudo docker --version

echo "Verificando se Docker Compose está instalado..."
if ! command -v docker-compose &> /dev/null
then
    echo "Docker Compose não está instalado. Baixando Docker Compose..."
    sudo curl -L "https://github.com/docker/compose/releases/download/$(curl -s https://api.github.com/repos/docker/compose/releases/latest | grep -Po '\"tag_name\": \"\K.*?(?=\")')/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
else
    echo "Docker Compose já está instalado."
fi

echo "Verificando a versão do Docker Compose..."
docker-compose --version

echo "Verificando se o arquivo docker-compose.yml existe..."
if [ ! -f docker-compose.yml ]; then
    echo "Arquivo docker-compose.yml não encontrado. Criando o arquivo docker-compose.yml..."
    cat <<EOL > docker-compose.yml
version: '3'

services:
  mysql:
    container_name: script-sql
    image: santthiago/script-sql:0.0.1
    restart: always
    ports:
      - "3306:3306"
    networks:
      - connection-mysqljava

  java:
    container_name: app-jar
    image: santthiago/app-jar
    restart: always
    stdin_open: true
    tty: true
    ports:
      - "8090:8080"
    depends_on:
      - mysql
    networks:
      - connection-mysqljava

volumes:
  mysql_data:

networks:
  connection-mysqljava:
EOL
else
    echo "Arquivo docker-compose.yml já existe."
fi

echo "Verificando se as imagens foram baixadas..."
sudo docker images

echo "Rodando o Docker Compose..."
sudo docker-compose up -d

echo "Executando Aplicação Java..."
sudo docker-compose run java

echo "Instalação e configuração concluídas com sucesso!"
