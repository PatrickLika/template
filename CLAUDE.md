# Template — Customer API

Spring Boot service implementing a Customer CRUD REST API, generated from an OpenAPI 3.0 spec using the OpenAPI Generator Gradle plugin.

## Build & Run

```bash
./gradlew bootRun          # run locally (H2 in-memory DB)
./gradlew test             # run integration tests
./gradlew build            # compile + test + jar
```

H2 console available at `http://localhost:8080/h2-console` when running locally.

## Architecture

Hexagonal (ports & adapters):

```
HTTP → CustomerDelegateImpl → customerServiceImpl → CustomerRepository → H2
         (inbound adapter)      (domain service)     (outbound adapter)
```

- **Inbound** — `dk.template.inbound`: `CustomerDelegateImpl` implements the generated `CustomersApi` interface; `RestMapperImpl` maps between OpenAPI DTOs and domain model.
- **Domain** — `dk.template.service`: `Customer` record, `CustomerService` / `CustomerAdapter` port interfaces, `customerServiceImpl`.
- **Outbound** — `dk.template.outbound`: `CustomerEntity` (JPA), `CustomerJpaRepository` (Spring Data), `CustomerRepository` (adapter).

## OpenAPI Code Generation

The spec lives at `src/main/java/openapi/customer-api.yaml`. The Gradle task `openApiGenerate` (runs before `compileJava`) generates the `CustomersApi` interface and request/response models into `build/generated/`.

Key generator options:
- `useSpringBoot3: true`
- `interfaceOnly: true` — only the interface is generated; `CustomerDelegateImpl` implements it directly. Without this, a conflicting `CustomersApiController` bean would also be generated.

## Testing

Integration tests in `customerDelegateImplIT` use `@SpringBootTest` with a real H2 database and `WebTestClient` pointed at the random port. Test schema and seed data are in `src/test/resources/`.

```bash
./gradlew test
```

## Dependencies of note

- Spring Boot 4.x (snapshot) + Java 26
- `spring-boot-starter-webflux` on the **test** classpath only — provides `WebTestClient` for integration tests against the servlet-based app.
- H2 is `runtimeOnly` (available in both app and tests).
