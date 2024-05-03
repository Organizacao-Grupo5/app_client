java -version
if [ $? = 0 ];
  then
    echo “java instalado”
  else
    echo “java não instalado”
    echo “gostaria de instalar o java? [s/n]”
    read get
  if [ \“$get\” == \“s\” ];
  then
  sudo apt install openjdk-17-jre -y
  java -jar app-client-api-1.0-SNAPSHOT-jar-with-dependencies.jar
  fi
fi