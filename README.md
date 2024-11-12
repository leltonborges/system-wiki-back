# System Wiki - CS/UDF

## Descrição do Projeto

O **System Wiki** é parte de um projeto acadêmico solicitado pela **Prof.ª Kadidja** na disciplina "Projeto de Linguagem
de Programação" do **7º semestre** do curso de **Ciência da Computação** no **Centro Universitário do Distrito Federal (
UDF)**.

O objetivo do sistema é facilitar o acesso a informações acadêmicas, oferecendo um espaço centralizado onde estudantes e
professores possam consultar e organizar conteúdos sobre tecnologias e ferramentas utilizadas no ambiente acadêmico.

## Integrantes

- **[Suelen Barbosa Marinho](https://github.com/suelenmarinho)**
- **[Lelton Pereira Borges](https://github.com/leltonborges)**

## Tecnologias

As seguintes ferramentas foram utilizadas no Backend:

- [Kotlin](https://kotlinlang.org/)
- [Quarkus Framework](https://quarkus.io/)
- [Hibernate ORM com Panache](https://pt.quarkus.io/guides/hibernate-orm-panache)
- [Gradle](https://gradle.org/)
- [Docker](https://docs.docker.com/engine/install/)

## Demonstrativo

### Swagger UI

Abaixo está um exemplo da interface Swagger gerada pela aplicação:

![Swagger UI](img/swagger-ui.png)

## Execução

### Build para execução local

Esta é a camada de serviço responsável por tratar as requisições feitas pela
aplicação [System Wiki Front](https://github.com/leltonborges/system-wiki-front).

#### Requisitos

- Banco de dados [MongoDB](https://www.mongodb.com/).

#### Passo 1: Executando uma instância local do MongoDB

Exemplo de configuração para execução com Docker Compose:

```yaml
services:
  mongo:
    image: mongo
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: example
      MONGO_INITDB_DATABASE: system-wiki
    ports:
      - "27017:27017"
    networks:
      - system-wiki

  mongo-express:
    image: mongo-express
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: root
      ME_CONFIG_MONGODB_ADMINPASSWORD: example
      ME_CONFIG_MONGODB_URL: mongodb://root:example@mongo:27017/
      ME_CONFIG_BASICAUTH: false
    ports:
      - "8081:8081"
    networks:
      - system-wiki

networks:
  system-wiki:
    name: system-wiki-net
    driver: bridge
```

#### Passo 2: Configurando as variáveis de ambiente

Configuração necessária para o banco de dados e CORS:

##### Banco de Dados:

- **`MONGODB_URL`** ou **`QUARKUS_MONGODB_CONNECTION_STRING`**: Define a URL de conexão com o banco de dados, incluindo
  autenticação e nome da base. Exemplo:
  ```
  mongodb://root:example@localhost:27017/system-wiki?authSource=admin
  ```
- **`MONGODB_DATABASE`** ou **`QUARKUS_MONGODB_DATABASE`**: Nome da base de dados. Exemplo:
  ```
  system-wiki
  ```

##### CORS:

- **`CORS`** ou **`QUARKUS_HTTP_CORS_ENABLED`**: Define se o CORS está habilitado. Valores possíveis: `true` ou `false`.
- **`CORS_ORIGINS`** ou **`QUARKUS_HTTP_CORS_ORIGINS`**: Define as origens permitidas. Para permitir todas as origens,
  use:
  ```
  /.*/
  ```

Exemplo de configuração no `application.yaml`:

```yaml
quarkus:
  application:
    name: System Wiki

  http:
    cors:
      enabled: ${CORS:true}
      origins: ${CORS_ORIGINS:/.*/}

  mongodb:
    connection-string: ${MONGODB_URL:mongodb://root:example@localhost:27017/system-wiki?authSource=admin}
    database: ${MONGODB_DATABASE:system-wiki}
```

#### Passo 3: Subindo a aplicação

Após as configurações do banco de dados e do CORS, execute o seguinte comando para rodar a aplicação:

```bash
./gradlew --console=plain quarkusDev
```

### Executando com Docker

#### Imagem do Docker

A imagem oficial está disponível no [Docker Hub](https://hub.docker.com/r/leltondev/system-wiki-back).

Exemplo de execução:

```bash
docker run -p 8080:8080 \
  -e CORS=true \
  -e CORS_ORIGINS=/.*/ \
  -e MONGODB_URL=mongodb://root:example@localhost:27017/system-wiki?authSource=admin \
  -e MONGODB_DATABASE=system-wiki \
  leltondev/system-wiki-back:1.1.0-jvm
```

#### Docker Compose

Exemplo de configuração com Docker Compose:

```yaml
services:
  mongo:
    image: mongo
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: example
      MONGO_INITDB_DATABASE: system-wiki
    ports:
      - "27017:27017"
    networks:
      - system-wiki

  mongo-express:
    image: mongo-express
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: root
      ME_CONFIG_MONGODB_ADMINPASSWORD: example
      ME_CONFIG_MONGODB_URL: mongodb://root:example@mongo:27017/
      ME_CONFIG_BASICAUTH: false
    ports:
      - "8081:8081"
    networks:
      - system-wiki

  backend:
    image: leltondev/system-wiki-back:1.1.0-jvm
    environment:
      CORS: true
      CORS_ORIGINS: /.*/
      MONGODB_URL: mongodb://root:example@mongo:27017/system-wiki?authSource=admin
      MONGODB_DATABASE: system-wiki
    ports:
      - "8080:8080"
    networks:
      - system-wiki

networks:
  system-wiki:
    name: system-wiki-net
    driver: bridge
```