# Campus Cuts

Find student-run, on-campus beauty and grooming providers (barbers, lash techs, nail techs, braiders, loctitians) at your school — browse profiles, prices, and reviews, then message a provider directly to arrange a time.

## Stack

Java 21, Spring Boot (Web, Data JPA, Security), PostgreSQL, Flyway, Thymeleaf, Maven.

Requires a local JDK 21 and Maven install (no Maven wrapper is checked in yet).

## Local development

1. Start Postgres: `docker compose up -d`
2. Run the app: `mvn spring-boot:run` (uses the `dev` profile by default via `application.yml`)
3. App is served at http://localhost:8080

## Running tests

Tests run against an in-memory H2 database in PostgreSQL-compatibility mode, so no Docker/Postgres is required:

```
mvn test
```

## Project layout

- `entity/` — JPA entities
- `repository/` — Spring Data repositories
- `service/` — business logic
- `controller/` — MVC controllers
- `dto/` — form-backing objects for controllers
- `security/` — Spring Security principal/user-details wiring
- `config/` — Spring configuration (security, MVC)
- `src/main/resources/db/migration/` — Flyway migrations (schema + reference data seeds)
- `src/main/resources/templates/` — Thymeleaf views
