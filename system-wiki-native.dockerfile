# Etapa de build
FROM quay.io/quarkus/ubi-quarkus-native-image:21.3-java11 AS build

WORKDIR /work

# Copia todos os arquivos do projeto para o contêiner
COPY . .

# Concede permissão de execução ao gradlew (necessário para Linux)
RUN chmod +x ./gradlew

# Compila a aplicação em modo nativo
RUN ./gradlew quarkusNative -Dquarkus.package.type=native -DskipTests

# Etapa de execução para binário nativo
FROM quay.io/quarkus/quarkus-micro-image:2.0

WORKDIR /work

# Copia o binário nativo gerado para o contêiner final
COPY --from=build /work/build/*-runner /work/application

EXPOSE 8080

# Comando para executar o binário nativo
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
