FROM openjdk:21-jdk-slim

WORKDIR /usr/src/app

## Executa app em modo de desenvolvimento para live reload
ENTRYPOINT ["./gradlew", "bootRun", "--no-daemon"]

