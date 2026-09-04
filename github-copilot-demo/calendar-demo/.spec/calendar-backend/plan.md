---
status: approved
---
# Plan: Calendar Backend

## Architecture
A single Spring Boot (Java 17, Spring Boot 4.1.1) REST application, purely backend, persisted in MySQL 8.4. Layered internally (controller -> service -> repository) but tested through a single end-to-end seam (Bruno HTTP smoke tests against a running server + real MySQL).

```
HTTP (REST) -> CalendarBackendApplication (Spring Boot)
  -> Controllers (REST endpoints)
  -> Services (business logic: recurrence, conflicts, reminders)
  -> Repositories (Spring Data JPA)
  -> MySQL (calendar_db, Flyway-managed schema)
```

## Components
- **Entities** (JPA): `Event`, `RecurrenceRule`, `Attendee`, `Reminder`
- **Repositories**: `EventRepository`, `AttendeeRepository`, `ReminderRepository`
- **Services**: `EventService` (CRUD + conflict detection), `RecurrenceService` (materialize occurrences), `AvailabilityService` (conflict/availability check)
- **Controllers**: `EventController` (REST endpoints)
- **DTOs**: request/response records for create/update/read
- **Flyway migrations**: `V1__init.sql` creating tables
- **springdoc-openapi**: serves `/swagger-ui.html` and `/v3/api-docs`

## Data model
- `events`: id, title, description, location, start_time, end_time, created_at, updated_at
- `recurrence_rules`: id, event_id (FK), frequency (DAILY/WEEKLY/MONTHLY), interval, until
- `attendees`: id, event_id (FK), email, status (ACCEPTED/DECLINED/TENTATIVE/PENDING)
- `reminders`: id, event_id (FK), method (EMAIL/POPUP), minutes_before

## REST API
- `POST /api/events` — create event (with optional recurrence, attendees, reminders)
- `GET /api/events` — list events (optionally `?from=&to=` range filter)
- `GET /api/events/{id}` — get event
- `PUT /api/events/{id}` — update event
- `DELETE /api/events/{id}` — delete event
- `GET /api/events/{id}/occurrences` — materialized occurrences of a recurring event
- `GET /api/events/availability?from=&to=` — availability/conflict check (returns overlapping events)
- `GET /api/events/{id}/attendees` — list attendees
- `POST /api/events/{id}/attendees` — add attendee
- `PATCH /api/events/{id}/attendees/{attendeeId}` — update attendee status

## Key decisions / tradeoffs
- **Single seam (Bruno smoke tests)**: per user preference, verification is a Bruno collection of happy-scenario smoke tests against a running app + real MySQL — no MockMvc/Testcontainers, no internal mocking. This gives high-confidence end-to-end coverage with minimal scaffolding.
- **Recurrence**: store the rule (frequency/interval/until) on the event; materialize occurrences on-demand via `GET /occurrences` rather than pre-generating rows — simpler, no unbounded storage, and matches "materialized/queryable" acceptance criterion.
- **Conflicts**: availability endpoint queries overlapping events in the requested range and returns them; no hard rejection on create (client decides), matching "reports availability/conflicts" criterion.
- **Reminders**: modeled as rows (method + minutes_before) stored and returned; no actual email/push delivery (non-goal).
- **No auth**: single-user MVP, no Spring Security (non-goal.
- **Flyway**: schema versioning so migrations are reproducible across environments.

## Risks
- MySQL root password/credentials must match `application.properties` — will wire via env vars with sensible defaults.

- Recurrence edge cases (monthly on 31st, DST) are out of scope (non-goal); basic UTC handling only.

- Bruno CLI must be installed to run the smoke tests; the Makefile `test` target will invoke it.



## Testing seam
**Single seam**: Bruno collection with smoke tests for happy scenarios. Tooling: the `bruno` CLI (installed via `npx skills add ... --skill bruno` per the skills-lock) driving HTTP requests against a running Spring Boot server + real MySQL. The Makefile `test` target runs the Bruno collection against the app. No MockMvc/Testcontainers, no internal mocking — assertions land on HTTP status/response body/read-back state.



## API docs
**springdoc-openapi-starter-webmvc-ui** (already scaffolded). Serves the OpenAPI spec at `/v3/api-docs` and the Swagger UI at `/swagger-ui.html`. No extra config needed beyond the dependency (auto-configured).