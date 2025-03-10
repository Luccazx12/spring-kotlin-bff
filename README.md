# Spring BFF Kotlin

Este projeto é uma prova de conceito para o *Backend for Frontend* (BFF) em Kotlin utilizando Docker, Gradle, Spring e um *Makefile* para automatizar o processo de construção e execução.

## Pré-requisitos

1. **Java 21**  
   Instale o Java 21. Utilize o comando abaixo ou uma ferramenta de gerenciamento de versões como SDKMAN!, JEnv ou `update-alternatives` (para Linux).

   ```bash
   sudo apt update && sudo apt install openjdk-21-jdk
   ```

2. **Docker**  
   Certifique-se de que o Docker e o Docker Compose estão instalados. Consulte a [documentação oficial do Docker](https://docs.docker.com/get-docker/) para mais detalhes.

3. **Gradle**  
   O Gradle é necessário para compilar o projeto. Ele será usado automaticamente pelo script incluído no repositório.

## Configuração Inicial

1. **Criar arquivo de variáveis de ambiente**
   Para conseguirmos alterar as dependências e configurações de acordo com o ambiente em que o código
   está sendo executado, podemos utilizar as variáveis de ambiente. Para isso crie um arquivo .env baseado
   no .env.example: 

   ```bash
   cp .env.example .env
   ```

2. **Criar a Rede Docker**  
   Antes de subir os containers, é necessário criar a rede Docker que será utilizada:

   ```bash
   make network
   ```

3. **Compilar o JAR**  
   Para compilar o JAR do projeto, execute:

   ```bash
   make build-jar
   ```

4. **Construir a Imagem Docker**  
   Após gerar o JAR, construa a imagem Docker:

   ```bash
   make docker-build
   ```

## Subindo o Projeto

Para subir o projeto basta utilizar o seguinte comando: 

   ```bash
   make up logs
   ```

## Comandos Disponíveis

Os comandos abaixo são gerenciados pelo *Makefile*. Use `make <comando>` para executá-los:

| Comando           | Descrição                                                                 |
|--------------------|---------------------------------------------------------------------------|
| `help`            | Mostra a lista de comandos disponíveis.                                   |
| `build-jar`       | Compila o JAR usando Gradle, chamando o script `scripts/build-jar.sh`.    |
| `docker-build`    | Constrói a imagem Docker.                                                 |
| `up`              | Sobe os containers usando Docker Compose.                                |
| `logs`            | Mostra os logs dos containers em execução.                               |
| `down`            | Para e remove os containers.                                             |
| `clean`           | Remove o JAR e limpa os containers, incluindo volumes e imagens.         |
| `network`         | Cria a rede Docker necessária para o projeto.                            |
| `rebuild`         | Rebuilda e sobe os containers da aplicação.                              |

## Uso do Spring no Projeto

Este projeto utiliza o **Spring Boot** para fornecer a estrutura principal do *Backend for Frontend*. O Spring foi integrado com Kotlin para facilitar o desenvolvimento e a criação de APIs reativas.

### Dependências

As principais dependências do Spring Boot no projeto incluem:

- **Spring Boot Starter Web**: Para expor APIs RESTful.
- **Spring Boot Starter Data MongoDB**: Para integração com o MongoDB.
- **Spring Boot Starter Data Redis** e **Reactive Redis**: Para armazenamento em cache e operações reativas.
- **Spring Boot DevTools**: Para desenvolvimento interativo.
- **Spring Boot Docker Compose**: Para facilitar a configuração do ambiente com Docker Compose.

### Funcionalidades do Spring Boot

- **MongoDB**: Usado como banco de dados principal para armazenamento de dados do usuário, transações e outros dados relacionados.
- **Redis**: Utilizado para caching e armazenamento temporário, melhorando a performance do aplicativo.

## Estrutura do Projeto

```plaintext
spring-kotlin-bff/
├── build/
├── scripts/
│   └── build-jar.sh      # Script para compilar o JAR
├── .env                  # Variáveis de ambiente do projeto
├── docker-compose.yml    # Arquivo de configuração do Docker Compose
├── Makefile              # Automação de tarefas
├── src/                  # Código-fonte do projeto
└── README.md             # Este arquivo
```

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou novas funcionalidades. Antes de enviar um *pull request*, certifique-se de que seu código segue as práticas do projeto e está devidamente testado.

```
