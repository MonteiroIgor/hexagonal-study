# hexagonal-study

## Overview

`hexagonal-study` é um projeto Spring Boot que demonstra a aplicação do padrão de arquitetura hexagonal (Ports and Adapters) para cadastro e validação de clientes.

A implementação atual inclui:

- REST controller para cadastro, consulta, atualização e remoção de clientes.
- Use cases que delegam regras de negócio aos ports e adaptadores.
- Integração Kafka para envio e recepção de eventos de validação de CPF.
- Configuração MongoDB para persistência.
- MapStruct para conversão entre DTOs e modelos de domínio.
- Feign client habilitado para potencial integração com serviços externos.

## Implementação

### Camadas principais

- `application.core.domain` — classes de domínio `Customer` e `Address`.
- `application.core.usecase` — caso de uso `InsertCustomerUseCase` com orquestração de busca de endereço, persistência e envio de CPF para validação.
- `application.ports.in` — portas de entrada para operações de cliente (`Insert`, `Find`, `Update`, `Delete`).
- `application.ports.out` — portas de saída para persistência, busca de endereço e envio de CPF.
- `adapters/in/controller` — controlador REST e mapeadores de DTO.
- `adapters/in/consumer` — consumidor Kafka que escuta validações de CPF.
- `config` — configuração Kafka de producer/consumer.

### Comportamento atual

- `POST /api/v1/customers`
  - Recebe `CustomerRequest` com `name`, `cpf` e `zipCode`.
  - Busca o endereço a partir do CEP via port de saída.
  - Persiste o cliente via port de saída de inserção.
  - Envia o CPF para validação através de Kafka.
- `GET /api/v1/customers/{id}`
  - Retorna o cliente com `name`, `cpf`, `address` e `isValidCpf`.
- `PUT /api/v1/customers/{id}`
  - Atualiza o cliente existente a partir do corpo de requisição.
- `DELETE /api/v1/customers/{id}`
  - Remove o cliente pelo ID.

### Integração Kafka

- `KafkaProducerConfig` configura producer para `localhost:9092`.
- `KafkaConsumerConfig` configura consumer para o tópico `tp-cpf-validate` e grupo `monteiro`.
- `ReceiveValidateCpfConsumer` consome mensagens `CustomerMessage` e encaminha para `UpdateCustomerInputPort`.

## Projeto

- `src/main/java/com/monteiro/hexagonal_study`
  - `adapters/in/controller` — controlador REST e mapeadores.
  - `adapters/in/consumer` — consumidor Kafka e mapeamento de mensagem.
  - `application/core/domain` — entidades de domínio.
  - `application/core/usecase` — casos de uso da aplicação.
  - `application/ports/in` — interfaces de entrada.
  - `application/ports/out` — interfaces de saída.
  - `config` — configuração de Kafka.

## Build and Run

### Requirements

- Java 21
- Maven 3.9+

### Build

```bash
./mvnw clean package
```

### Run

```bash
./mvnw spring-boot:run
```

Ou execute o JAR gerado:

```bash
java -jar target/hexagonal-study-0.0.1-SNAPSHOT.jar
```

## Testing

Execute os testes com:

```bash
./mvnw test
```

## Configuration

A configuração principal está em `src/main/resources/application.yml`.

Valores atuais:

- `spring.mongodb.uri` = `mongodb://localhost:27017/hexagonal`
- `monteiro.client.address.url` = `http://locahost:8082/addresses` (nota: o host está configurado como `locahost` no arquivo atual)
- Kafka broker local: `localhost:9092`

> Observação: os beans de Kafka em `KafkaConsumerConfig` e `KafkaProducerConfig` usam `localhost:9092` como broker padrão.

## API Contract

### CustomerRequest

```json
{
  "name": "João Silva",
  "cpf": "12345678901",
  "zipCode": "01000-000"
}
```

### CustomerResponse

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

### Endpoints

- `POST /api/v1/customers`
  - Cria um cliente.
  - Retorna `200 OK` com corpo vazio no estado atual.
- `GET /api/v1/customers/{id}`
  - Retorna `200 OK` com o cliente.
- `PUT /api/v1/customers/{id}`
  - Atualiza o cliente.
  - Retorna `204 No Content`.
- `DELETE /api/v1/customers/{id}`
  - Remove o cliente.
  - Retorna `204 No Content`.

## cURL Examples

### Create customer

```bash
curl -X POST http://localhost:8080/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "cpf": "12345678901",
    "zipCode": "01000-000"
  }'
```

### Get customer

```bash
curl http://localhost:8080/api/v1/customers/{id}
```

### Update customer

```bash
curl -X PUT http://localhost:8080/api/v1/customers/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva Atualizado",
    "cpf": "12345678901",
    "zipCode": "01000-000"
  }'
```

### Delete customer

```bash
curl -X DELETE http://localhost:8080/api/v1/customers/{id}
```

## Kafka Example

### Topic

- `tp-cpf-validate`

### Payload

```json
{
  "name": "João Silva",
  "cpf": "12345678901",
  "zipCode": "01000-000"
}
```

O consumidor `ReceiveValidateCpfConsumer` escuta esse tópico e encaminha a mensagem para o `UpdateCustomerInputPort`.

## Observações

- A implementação usa `@EnableFeignClients` na classe principal, mas a integração real com um client Feign de endereço ainda depende de implementação adicional.
- O projeto serve como estudo de arquitetura; algumas portas de saída (`InsertCustomerOutputPort`, `FindAddressByZipCodeOutputPort`, `SendCpfForValidationOutputPort`) são definidas como contratos e não estão necessariamente mapeadas para um adapter completo dentro do código presente.

## License

Adicione a licença apropriada para sua organização ou projeto.
