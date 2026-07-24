 # hexagonal-study

## Overview

`hexagonal-study` is a Spring Boot proof-of-concept that demonstrates a hexagonal architecture for customer registration and integration workflows. The project is designed for enterprise-grade maintainability, separation of concerns, and clean layer boundaries.

## Key Principles

- Hexagonal architecture (Ports and Adapters)
- Domain-centric design in `application.core.domain`
- Input ports for use case orchestration
- Controller adapters for external REST requests
- MapStruct mapping between request DTOs and domain models
- Spring Boot auto-configuration with Kafka, MongoDB, validation, and Feign support

## Project Structure

- `src/main/java/com/monteiro/hexagonal_study`
  - `adapters/in/controller` — REST controller layer and request mapping
  - `adapters/in/controller/mapper` — MapStruct mappers for DTO conversion
  - `application/core/domain` — domain entities and value objects
  - `application/core/usecase` — application use cases and business orchestration
  - `application/ports/in` — inbound port interfaces for driving application behavior
  - `config` — application configuration classes

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

Or run the generated JAR:

```bash
java -jar target/hexagonal-study-0.0.1-SNAPSHOT.jar
```

## Testing

Execute unit and integration tests with:

```bash
./mvnw test
```

## Configuration

Application configuration is centralized in `src/main/resources/application.yml`.

Externalize runtime settings for:

- MongoDB connectivity
- Kafka topics and bootstrap servers
- Validation rules and profiles

## Conventions

- Keep business rules inside `application.core.domain`
- Drive use cases through port interfaces in `application.ports.in`
- Keep adapter code isolated from domain logic
- Use MapStruct for mapping DTOs and domain models

## Notes

This repository is intended as an architectural study, not a production-ready reference implementation. It provides a foundation for building scalable microservices using hexagonal patterns and Spring Boot integrations.

## License

Add the appropriate license details for your organization or project governance.

## API Endpoints

Abaixo estão os contratos REST expostos por esta aplicação (exemplos para estudo). Ajuste conforme os controladores reais.

- Create Customer
  - Method: POST
  - URL: /api/v1/customers
  - Request (application/json):

```json
{
  "name": "João Silva",
  "cpf": "12345678901",
  "email": "joao.silva@example.com",
  "address": {
    "street": "Rua Exemplo",
    "city": "São Paulo",
    "zipCode": "01000-000",
    "state": "SP"
  }
}
```

  - Success: 201 Created
    - Response body: created resource location in `Location` header and minimal payload with `id`.
  - Errors: 400 Bad Request (validation errors), 409 Conflict (duplicate CPF)

- Get Customer
  - Method: GET
  - URL: /api/v1/customers/{id}
  - Success: 200 OK (customer payload)
  - Errors: 404 Not Found

- Domain Event (example)
  - Topic: `customers.created` (Kafka)
  - Payload: minimal domain event with `customerId`, `occurredAt` and `payload` (refer to producer adapter)

Notes:

- Os exemplos acima servem como contrato inicial; sincronize-os com os controladores reais em `src/main/java/com/monteiro/hexagonal_study/adapters/in/controller`.
- Se desejar, eu posso gerar a documentação OpenAPI/Swagger automaticamente e adicionar instruções de configuração.
