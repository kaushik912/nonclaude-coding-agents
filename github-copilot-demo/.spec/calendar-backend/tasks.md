---
status: approved
---
# Tasks: Calendar Backend

Testing seam: **single** — Bruno collection (`bru run`) of happy-scenario smoke tests against a running app + real MySQL. Each behavior task is paired with a Bruno smoke-test task that asserts on HTTP status/response body/read-back state.

- [x] Scaffold Spring Boot project with dependencies (web, data-jpa, mysql, validation, lombok, springdoc-openapi, flyway) via `spring init`
- [x] Configure `application.properties` for MySQL (env-var-driven DB creds), JPA validate, Flyway, springdoc
- [x] Write Flyway migration `V1__init.sql` creating `events`, `recurrence_rules`, `attendees`, `reminders` tables
- [x] Write Bruno smoke test: create event returns 201 and persists (read-back via GET)
- [x] Implement Event entity + EventRepository + EventService.create + EventController POST/GET
- [x] Write Bruno smoke test: update event returns 200 and reflects changes
- [x] Implement EventService.update + EventController PUT
- [x] Write Bruno smoke test: delete event returns 204 and is gone (GET 404)
- [x] Implement EventService.delete + EventController DELETE
- [x] Write Bruno smoke test: recurring event occurrences are materialized/queryable
- [x] Implement RecurrenceRule entity + RecurrenceService + GET /occurrences
- [ ] Write Bruno smoke test: add attendee + update attendee status
- [ ] Implement Attendee entity + attendee endpoints (POST/PATCH/GET)
- [ ] Write Bruno smoke test: availability/conflict check reports overlapping events
- [ ] Implement AvailabilityService + GET /availability
- [ ] Write Bruno smoke test: reminders are stored and returned
- [ ] Implement Reminder entity + reminder handling in event create/read
- [ ] Write Bruno smoke test: /swagger-ui.html and /v3/api-docs are reachable (200/3xx)
- [ ] Write Makefile with `test` target running the Bruno collection (literal command)