#!/bin/bash

echo "Atualizando o sistema..."
sudo apt-get update -y
sudo apt-get upgrade -y

echo "Instalando Docker..."
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

echo "Verificando a instalação do Docker..."
sudo docker --version

echo "Baixando as imagens Docker..."
sudo docker pull santthaigo/script-sql:0.0.1
sudo docker pull santthaigo/app-jar:0.0.1

echo "Verificando se as imagens foram baixadas..."
sudo docker images

echo "Instalação concluída com sucesso!"

