FROM openjdk:21-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /usr/src/app

# Copia o script de build para o diretório de trabalho
COPY .setup/scripts/build-jar.sh .

# Dá permissão de execução ao script de build
RUN chmod +x build-jar.sh

# Executa o script de build
RUN ./build-jar.sh --no-cache

# Copia o JAR gerado para o diretório de trabalho
COPY build/libs/mobile-bff-1.0.0.jar app.jar

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
