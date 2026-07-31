# hexagonal-study

## Visão Geral

O projeto `hexagonal-study` é uma aplicação Spring Boot desenvolvida para demonstrar a implementação prática do padrão de arquitetura hexagonal (Ports and Adapters), com foco em cadastro, consulta, atualização e remoção de clientes.

A solução atual integra:

- API REST para gestão de clientes;
- Casos de uso para orquestração da regra de negócio;
- Persistência em MongoDB;
- Mensageria com Kafka para validação assíncrona de CPF;
- Mapeamento entre DTOs e modelo de domínio com MapStruct;
- Integração com um serviço de endereço via Feign.

## Arquitetura

A estrutura do projeto segue a separação entre domínio, portas e adaptadores:

- `application/core/domain` — modelos de domínio, como `Customer` e `Address`.
- `application/core/usecase` — implementações dos casos de uso, como `InsertCustomerUseCase`, `FindCustomerByIdUseCase`, `UpdateCustomerUseCase` e `DeleteCustomerByIdUseCase`.
- `application/ports/in` — portas de entrada da aplicação.
- `application/ports/out` — portas de saída para persistência, busca de endereço e envio de eventos.
- `adapters/in/controller` — camada de entrada REST.
- `adapters/in/consumer` — consumidor Kafka.
- `adapters/out` — adaptadores concretos para MongoDB, consulta de endereço e publicação em Kafka.
- `config` — configuração de producer e consumer Kafka.

## Fluxo de Negócio

Os principais fluxos implementados são:

- `POST /api/v1/customers` — cria um cliente, busca o endereço pelo CEP, salva o registro e envia o CPF para validação.
- `GET /api/v1/customers/{id}` — consulta um cliente pelo identificador.
- `PUT /api/v1/customers/{id}` — atualiza um cliente existente.
- `DELETE /api/v1/customers/{id}` — remove um cliente.

## Integração com Kafka

A aplicação utiliza Kafka para processamento assíncrono de validação de CPF. O fluxo inclui:

- `KafkaProducerConfig` e `KafkaConsumerConfig` para configuração do broker local;
- envio de mensagens para o tópico `tp-cpf-validate`;
- consumo das mensagens por `ReceiveValidateCpfConsumer`, que encaminha a atualização ao caso de uso apropriado.

## Execução Local

### Pré-requisitos

- Java 21
- Maven 3.9+
- Docker Desktop para subir os serviços auxiliares

### Subir infraestrutura local

```bash
docker compose -f docker-local/docker-compose.yml up -d
```

Os containers disponibilizam:

- Kafka e Zookeeper;
- Kafdrop;
- MongoDB;
- Mongo Express.

### Build

```bash
./mvnw clean package
```

### Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8081
```

## Configuração

O arquivo principal de configuração está em `src/main/resources/application.yml`.

Valores configurados atualmente:

- Porta da aplicação: `8081`
- MongoDB local: `localhost:27017`
- Banco MongoDB: `hexagonal-study`
- Usuário MongoDB: `root`
- Senha MongoDB: `example`
- Endpoint de endereço: `http://localhost:8082/addresses`
- Broker Kafka local: `localhost:9092`

## Contrato da API

### Requisição: CustomerRequest

```json
{
  "name": "João Silva",
  "cpf": "12345678901",
  "zipCode": "01000-000"
}
```

### Resposta: CustomerResponse

```json
{
  "name": "João Silva",
  "cpf": "12345678901",
  "isValidCpf": false,
  "address": {
    "street": "Rua Exemplo",
    "city": "São Paulo",
    "state": "SP"
  }
}
```

## Exemplos de Requisições

### Criar cliente

```bash
curl -X POST http://localhost:8081/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "cpf": "12345678901",
    "zipCode": "01000-000"
  }'
```

### Consultar cliente

```bash
curl http://localhost:8081/api/v1/customers/{id}
```

### Atualizar cliente

```bash
curl -X PUT http://localhost:8081/api/v1/customers/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva Atualizado",
    "cpf": "12345678901",
    "zipCode": "01000-000"
  }'
```

### Excluir cliente

```bash
curl -X DELETE http://localhost:8081/api/v1/customers/{id}
```

## Considerações Finais

Este projeto funciona como um estudo prático de arquitetura hexagonal, demonstrando a separação entre domínio, casos de uso e adaptadores para integração com sistemas externos e infraestrutura.
