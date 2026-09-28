# rest-api

Product CRUD REST demo (pi-agent demo). Spring Boot + H2 + Redis cache.

## Stack
- Spring Boot 4.1.1, Java 17, Maven wrapper (`./mvnw`).
- webmvc, data-jpa, H2 (in-memory `jdbc:h2:mem:products`), actuator, cache + data-redis.

## Commands
- Run: `./mvnw spring-boot:run` (port default 8080)
- Test: `./mvnw test`
- Example curls: `curls.md` (`/products` POST/GET/GET {id}/DELETE)
- H2 console: `/h2-console`

## Config
- `application.properties`: `spring.cache.type=redis`, Redis localhost:6379, TTL 60000ms, `ddl-auto=update`, `show-sql=true`.
- No env vars; hardcoded localhost Redis.

## Layout
`com.example.restapi`: `controller/ProductController`, `service/ProductService`, `repository/ProductRepository`, `model/Product`.

## Gotchas
- Cache is Redis, so a local Redis on 6379 is needed for cached paths (no Docker files in repo; ask user before using Docker).
- Only 1 test class (`RestApiApplicationTests`, context load).
