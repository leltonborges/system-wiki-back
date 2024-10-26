# Etapa de build
FROM quay.io/quarkus/ubi-quarkus-native-image:21.3-java11 AS build

WORKDIR /work

# Copia o código-fonte
COPY . .

# Compila a aplicação em modo JVM
RUN ./mvnw package -Dquarkus.package.type=jar -DskipTests

# Etapa de execução
FROM eclipse-temurin:11-jre AS run

WORKDIR /work
COPY --from=build /work/target/*-runner.jar /work/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/work/app.jar"]
