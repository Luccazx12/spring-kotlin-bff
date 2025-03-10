# Variáveis
JAR_PATH := build/libs/mobile-bff-1.0.0.jar
DOCKER_IMAGE := mobile-bff:1.0.0
DOCKER_COMPOSE := docker-compose -f docker-compose.yml
NETWORK_NAME := spring-kotlin-bff-network
BUILD_SCRIPT=./.setup/scripts/build-jar.sh
DOCKER_BFF_SERVICE=spring-kotlin-bff

# Alvos
.PHONY: help build-jar docker-build up logs down clean network rebuild

help:
	@echo "Comandos disponíveis:"
	@echo "  make build-jar       - Compila o JAR usando Gradle."
	@echo "  make docker-build    - Constrói a imagem Docker."
	@echo "  make up              - Sobe os containers com Docker Compose."
	@echo "  make logs            - Mostra os logs dos containers."
	@echo "  make down            - Para e remove os containers."
	@echo "  make clean           - Remove o JAR e limpa os containers."
	@echo "  make network         - Cria a rede Docker necessária."
	@echo "  make rebuild         - Rebuilda e sobe os containers."

network:
	@echo ">> Criando a rede Docker: $(NETWORK_NAME)..."
	docker network create $(NETWORK_NAME) || echo ">> Rede já existe: $(NETWORK_NAME)."

build-jar:
	@echo ">> Chamando o script para compilar o JAR..."
	@chmod +x $(BUILD_SCRIPT)  # Adiciona permissão de execução
	$(BUILD_SCRIPT) --no-cache $(ARGS);

docker-build: network build-jar
	@echo ">> Construindo a imagem Docker: $(DOCKER_IMAGE)..."
	$(DOCKER_COMPOSE) build --build-arg GITLAB_PRIVATE_TOKEN=${GITLAB_PRIVATE_TOKEN}

up:
	@echo ">> Subindo os containers com Docker Compose..."
	$(DOCKER_COMPOSE) up -d $(DOCKER_BFF_SERVICE)

rebuild: network docker-build
	@echo ">> Subindo os containers com Docker Compose..."
	$(DOCKER_COMPOSE) up --build -d $(DOCKER_BFF_SERVICE)

logs:
	@echo ">> Exibindo os logs dos containers..."
	$(DOCKER_COMPOSE) logs -f

down:
	@echo ">> Parando e removendo os containers..."
	$(DOCKER_COMPOSE) down

clean:
	@echo ">> Removendo o JAR e limpando os containers..."
	@if [ -f "$(JAR_PATH)" ]; then \
		rm -f $(JAR_PATH); \
		echo ">> JAR removido: $(JAR_PATH)."; \
	fi
	$(DOCKER_COMPOSE) down --rmi all --volumes --remove-orphans

bash:
	$(DOCKER_COMPOSE) run  sh

#
# Shell
#
shell:
	docker-compose run --rm -w ${PWD} $(DOCKER_BFF_SERVICE) sh

shell-with-ports:
	docker-compose run --rm -w ${PWD} --service-ports $(DOCKER_BFF_SERVICE) sh
