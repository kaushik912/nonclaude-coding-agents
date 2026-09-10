# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repo layout

This repo currently contains one project: `calendar-backend/` (Spring Boot REST API). Spec/plan/task tracking for it lives in `.spec/calendar-backend/` (`spec.md`, `plan.md`, `tasks.md`) — check `tasks.md` for what's done vs. pending before starting new work.

## Commands (run from `calendar-backend/`)

- Build: `./mvnw compile`
- Run app: `./mvnw spring-boot:run` (needs MySQL running locally, see below)
- Unit/integration tests (JUnit): `./mvnw test`
- Run a single JUnit test: `./mvnw test -Dtest=ClassName#methodName`
- Smoke tests (Bruno, against a running app): `make test` — equivalent to `cd bruno && bru run events -r --env dev`. Requires the Bruno CLI (`bru`) installed and the app running on `localhost:8080`.
- Run a single Bruno request: `cd bruno && bru run events/<file>.bru --env dev`

There is no local MySQL/Docker setup checked into this repo. The app expects a MySQL instance reachable via `DB_URL`/`DB_USERNAME`/`DB_PASSWORD` env vars (defaults in `application.properties` point at `localhost:3306/calendar_db`, root/local creds). Per project policy, don't reach for Docker to stand up MySQL — ask the user first.

## Architecture

Single Spring Boot 4.1 (Java 17) app, layered `Controller -> Service -> Repository -> MySQL`, schema managed by Flyway (`src/main/resources/db/migration/V1__init.sql`), API documented via springdoc-openapi (`/swagger-ui.html`, `/v3/api-docs`). Entities use Lombok for getters/setters/constructors — don't hand-write boilerplate that Lombok annotations already generate.

Package layout mirrors domain concepts under `com.example.calendar`:
- `event/` — `Event` entity, `EventRepository`, `EventService` (CRUD, delegates recurrence/attendee/reminder persistence), `EventController` (all `/api/events*` routes including `/availability` and `/{id}/occurrences`), `EventRequest`/`EventResponse` DTOs.
- `recurrence/` — `RecurrenceRule` entity + `RecurrenceService`. Recurrence is **not** pre-materialized: rules (frequency/interval/until) are stored once per event, and occurrences are computed on-demand in `RecurrenceService.materialize()` when `GET /api/events/{id}/occurrences?from=&to=` is called.
- `attendee/` — `Attendee` entity/status enum + controller/service/repository, nested under an event.
- `reminder/` — `Reminder` entity + repository only (no service layer; `EventService` persists/reads reminders directly).
- `availability/` — `AvailabilityService.findConflicts()`, backing `GET /api/events/availability?from=&to=`. It queries `EventRepository.findOverlapping()` and returns overlapping events — availability is informational only, there's no hard rejection on create when times conflict (client decides).
- `GlobalExceptionHandler.java` (top-level, not per-package) — maps domain exceptions (`EventNotFoundException`, `AttendeeNotFoundException`) to HTTP responses.

Key design choices to preserve when extending this code:
- No auth/security layer (explicit non-goal — single-user MVP).
- No reminder delivery (email/push) — reminders are stored/returned data only.
- Testing seam is intentionally single-layered: Bruno HTTP smoke tests (`calendar-backend/bruno/events/*.bru`) hitting a running server + real MySQL, asserting on status/response body/read-back state. No MockMvc/Testcontainers/internal mocking is used — keep new endpoint tests in this same style, one `.bru` file per scenario, run via `make test`.

## Project conventions (from global rules)

- New Spring dependencies: check for `springdoc-openapi` already present before adding it (it already is — see pom.xml). Annotate new controllers with `@Tag`/`@Operation`.
- JUnit tests use Given/When/Then structure (see `~/github_projs/my-claude-skills` testing-style rule) — applies to any new `src/test/java` tests, not the Bruno suite.
- After fixing an API bug (status codes, validation, business logic, contract issues), offer a regression test per the `regression-testing` rule — default to a Bruno `.bru` test here since this is not currently RestAssured-based (matches the existing suite), unless the user prefers otherwise.
