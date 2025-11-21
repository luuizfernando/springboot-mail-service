# Spring Boot Mail Service

Sistema composto por dois serviços Spring Boot integrados via RabbitMQ para envio e consumo de mensagens, com persistência em PostgreSQL e orquestração opcional via Docker Compose.

## Visão Geral

- Dois módulos independentes:
  - `user` — API REST para cadastrar usuários e publicar mensagens na fila
  - `mailsender` — consumidor da fila `email-queue` que persiste mensagens
- Integração com RabbitMQ - CloudAMQP
- Persistência em PostgreSQL com JPA/Hibernate
- Docker Compose para bancos dedicados de cada módulo

## Tecnologias

- Java `21`
- Spring Boot `3.5.8`
- Spring AMQP (RabbitMQ)
- Spring Data JPA/Hibernate
- Spring Web
- Spring Actuator (módulo `user`)
- Spring Mail (módulo `mailsender`)
- PostgreSQL
- Docker e Docker Compose
- Lombok

## Serviços Utilizados

- GitHub
- Docker
- CloudAMQP (RabbitMQ gerenciado)

## Pré-requisitos

- `Java 21` instalado
- `Docker` e `Docker Compose` instalados
- Portas disponíveis para os bancos:
  - `5433` (banco do módulo `user`)
  - `5434` (banco do módulo `mailsender`)
- Credenciais de acesso ao RabbitMQ (CloudAMQP ou local)

## Configuração de Ambiente

As configurações padrões estão em `application.properties` dentro de cada módulo. Você pode sobrescrever via variáveis de ambiente padrão do Spring (`SPRING_*`).

- Módulo `user` (`user/src/main/resources/application.properties`):
  - `server.port=8081`
  - `spring.datasource.url=jdbc:postgresql://localhost:5433/ms-user`
  - `spring.datasource.username=postgres`
  - `spring.datasource.password=postgres`
  - `spring.jpa.hibernate.ddl-auto=update`
  - RabbitMQ:
    - `spring.rabbitmq.host=<host>`
    - `spring.rabbitmq.port=<port>`
    - `spring.rabbitmq.username=<usuario>`
    - `spring.rabbitmq.password=<senha>`
    - `spring.rabbitmq.virtual-host=<vhost>`
    - `spring.rabbitmq.ssl.enabled=<true|false>`

- Módulo `mailsender` (`mailsender/src/main/resources/application.properties`):
  - `spring.datasource.url=jdbc:postgresql://localhost:5434/ms-email`
  - `spring.datasource.username=postgres`
  - `spring.datasource.password=postgres`
  - `spring.jpa.hibernate.ddl-auto=update`
  - RabbitMQ (chaves idênticas às acima)

## Subir Banco de Dados (Docker)

Cada módulo possui seu `docker-compose.yml` para subir o respectivo banco:

```bash
# Banco do módulo user (PostgreSQL em 5433)
cd user
docker-compose up -d postgres

# Banco do módulo mailsender (PostgreSQL em 5434)
cd ../mailsender
docker-compose up -d postgres
```

## Executar a Aplicação

- Via Maven Wrapper (Windows):

```bash
cd user
./mvnw.cmd spring-boot:run

cd ../mailsender
./mvnw.cmd spring-boot:run
```

- Via Maven Wrapper (Linux/Mac):

```bash
cd user
./mvnw spring-boot:run

cd ../mailsender
./mvnw spring-boot:run
```

## Endpoints Principais (módulo `user`)

- Usuários
  - `POST /users` — cria um novo usuário
    - Exemplo de corpo: `{"name":"Alice"}`

- Mensagens
  - `POST /send` — publica uma mensagem na fila `email-queue`
    - Espera corpo como texto simples (ex.: `Olá, mundo`)
    - Controller: `user/src/main/java/com/project/user/controllers/UserController.java:26-29`
  - `GET /messages/{id}` — retorna mensagens de um usuário pelo `id` (UUID)
    - Envie `id` em formato UUID; caso contrário, ocorrerá `400 Bad Request`
    - Controller: `user/src/main/java/com/project/user/controllers/UserController.java:31-34`

## Fluxo de Mensagens

- A API `user` publica o conteúdo recebido em `POST /send` na fila RabbitMQ `email-queue`.
- O serviço `mailsender` consome a fila (`@RabbitListener`) e persiste cada mensagem na tabela `TB_EMAIL`.

## Observações de Segurança

- Não versione segredos. Utilize variáveis de ambiente para `spring.rabbitmq.*` em produção.
- Proteja o acesso aos bancos e mantenha credenciais fortes.

## Versionamento

- `1.0.0`

## Autores

- `https://www.linkedin.com/in/luizfernando-react-java-fullstack/`

Obrigado por visitar e bons códigos!