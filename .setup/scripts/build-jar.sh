#!/bin/bash

# Configurações iniciais
JAR_PATH="build/libs/mobile-bff-1.0.0.jar"
GRADLE_CMD="./gradlew"
ARGS=""
INVALIDATE_CACHE=false
REPLACE=false

# Função de ajuda
usage() {
  echo "Uso: $0 [opções] [-- gradle-args]"
  echo
  echo "Opções:"
  echo "  --no-cache   Limpa o cache antes de construir o JAR."
  echo "  --replace            Substitui o JAR existente, se já existir."
  echo "  -h, --help           Mostra esta ajuda."
  echo
  echo "Exemplo:"
  echo "  $0 --no-cache --replace -- -Pprofile=dev"
  exit 0
}

# Processar argumentos
while [[ $# -gt 0 ]]; do
  case "$1" in
    --no-cache)
      INVALIDATE_CACHE=true
      shift
      ;;
    --replace)
      REPLACE=true
      shift
      ;;
    -h|--help)
      usage
      ;;
    --)
      shift
      ARGS="$@"
      break
      ;;
    *)
      echo "Opção desconhecida: $1"
      usage
      ;;
  esac
done

# Limpar o cache, se necessário
if [ "$INVALIDATE_CACHE" = true ]; then
  echo ">> Invalidando o cache do Gradle..."
  $GRADLE_CMD clean
fi

# Verificar se o JAR já existe
if [ -f "$JAR_PATH" ]; then
  if [ "$REPLACE" = true ]; then
    echo ">> Substituindo o arquivo existente: $JAR_PATH"
    rm -f "$JAR_PATH"
  else
    echo ">> O JAR já existe em $JAR_PATH. Use --replace para sobrescrevê-lo."
    exit 1
  fi
fi

# Gerar o JAR
echo ">> Gerando o JAR com os argumentos: $ARGS"
$GRADLE_CMD bootJar $ARGS

# Verificar sucesso
if [ -f "$JAR_PATH" ]; then
  echo ">> JAR gerado com sucesso em: $JAR_PATH"
else
  echo ">> Falha ao gerar o JAR."
  exit 1
fi
