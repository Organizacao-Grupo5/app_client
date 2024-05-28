#!/bin/bash

echo "Atualizando o sistema..."
sudo apt-get update -y
sudo apt-get upgrade -y


echo "Instalando MySQL..."
sudo apt-get install mysql-server -y


echo "Configurando MySQL..."
sudo systemctl start mysql
sudo systemctl enable mysql


MYSQL_ROOT_PASSWORD="Client123$"
sudo mysql -e "ALTER USER 'client'@'localhost' IDENTIFIED WITH mysql_native_password BY '$MYSQL_ROOT_PASSWORD';"
sudo mysql -e "FLUSH PRIVILEGES;"


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

# Adicionar usuário atual ao grupo Docker (mudar 'your_user' para o seu nome de usuário se necessário)
sudo usermod -aG docker $USER


echo "Instalando o Java..."
sudo apt-get install openjdk-11-jdk -y

# Configurar e rodar o arquivo JAR (mude 'your_app.jar' para o caminho do seu arquivo JAR)
JAR_PATH="/path/to/your_app.jar"

# Certifique-se de ter permissões adequadas no arquivo JAR
chmod +x $JAR_PATH

# Criar um serviço systemd para o JAR (opcional, mas recomendado para gerenciar a aplicação)
SERVICE_NAME="my-java-app"
sudo tee /etc/systemd/system/$SERVICE_NAME.service > /dev/null <<EOF
[Unit]
Description=My Java Application
After=network.target

[Service]
User=$USER
ExecStart=/usr/bin/java -jar $JAR_PATH
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF

# Iniciar e habilitar o serviço
sudo systemctl daemon-reload
sudo systemctl start $SERVICE_NAME
sudo systemctl enable $SERVICE_NAME

echo "Instalação e configuração concluídas!"
