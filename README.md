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

### Executar apenas os testes unitários

Para rodar somente os testes unitários (sem empacotar ou executar a aplicação), utilize o comando padrão do Maven:

```bash
./mvnw test
```

Exemplos úteis:

- Rodar apenas uma classe de teste específica:

```bash
./mvnw -Dtest=MinhaClasseTest test
```

- Rodar apenas um método de teste específico dentro de uma classe:

```bash
./mvnw -Dtest=MinhaClasseTest#meuMetodo test
```

- Exibir stacktraces completos / saída mais detalhada (útil para depuração):

```bash
./mvnw -DtrimStackTrace=false test
```

Observações:

- O plugin Surefire executa os testes durante a fase `test` (normalmente usado para testes unitários). O plugin Failsafe, quando configurado, executa testes de integração na fase `verify`. Para rodar também testes de integração, use `./mvnw verify`.
- Em geral, `./mvnw test` é suficiente para executar apenas os testes unitários do projeto.

---

### Teste de arquitetura (ArchUnit)

Este projeto inclui um teste de arquitetura ArchUnit localizado em `src/test/java/.../architecture/LayeredArchitectureTest.java`.

- Para executar somente o teste de arquitetura:

```bash
./mvnw -Dtest=LayeredArchitectureTest test
```

### Como ignorar o teste de arquitetura ao rodar os unitários

Se for necessário não executar o teste de arquitetura ao rodar os testes unitários, algumas abordagens práticas:

1) Rodar somente testes que seguem um padrão de nomes (recomendado se houver convenção de nomes para unit tests):

```bash
# exemplo: executar apenas classes que terminam com "*UnitTest"
./mvnw -Dtest=*UnitTest test
```

2) Mover o teste de arquitetura para a categoria de "integração" e usar Failsafe (prática comum):

- Renomear o teste para terminar em `*IT.java` (ex.: `LayeredArchitectureIT.java`) e deixá-lo em `src/test/java` ou em `src/integration-test/java` conforme sua convenção.
- Executar unitários com:

```bash
./mvnw test
```

- Executar integração/arquitetura com:

```bash
./mvnw verify
```

3) Excluir especificamente o arquivo via configuração do Surefire (no POM) ou usando um arquivo de exclusões. Exemplo (alteração no POM):

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <configuration>
    <excludes>
      <exclude>**/LayeredArchitectureTest.java</exclude>
    </excludes>
  </configuration>
</plugin>
```

Observação: algumas formas de exclusão/filtragem via linha de comando dependem da versão do Maven Surefire; por isso as opções 1 ou 2 (padrões de nome ou mover para Failsafe) são as mais portáteis. Se desejar, aplico uma das mudanças automaticamente (por exemplo, mover/renomear o teste para *IT ou adicionar a configuração do Surefire).

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
