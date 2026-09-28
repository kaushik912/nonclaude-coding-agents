# calendar-backend

Calendar REST API: events, recurrence, attendees, reminders, availability.
Parent `../CLAUDE.md` has fuller architecture; specs in `../.spec/calendar-backend/{spec,plan,tasks}.md` (check tasks.md first).

## Stack
- Spring Boot 4.1.1, Java 17, Maven wrapper (`./mvnw`).
- JPA + Flyway (`db/migration/V1__init.sql`) + MySQL (`ddl-auto=validate`), Lombok, springdoc 3.1.0, validation.

## Commands
- Compile: `./mvnw compile`
- Run: `./mvnw spring-boot:run` (needs local MySQL)
- JUnit: `./mvnw test`; single: `-Dtest=Class#method`
- Bruno smoke: `make test` (= `cd bruno && bru run events -r --env dev`); needs `bru` CLI + app up on localhost:8080.

## Env vars
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` (defaults in application.properties).
- Swagger UI `/swagger-ui.html`, spec `/v3/api-docs`.

## Layout
`com.example.calendar`: `event/`, `recurrence/`, `attendee/`, `reminder/`, `availability/`, `GlobalExceptionHandler`.

## Gotchas
- No Docker/MySQL setup in repo; user policy: ask before using Docker.
- Recurrence computed on demand (`RecurrenceService.materialize()`), not stored.
- Tests are Bruno HTTP smoke (`bruno/events/*.bru`), one file per scenario.
- No auth, no reminder delivery.
