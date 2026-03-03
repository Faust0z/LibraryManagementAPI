# Library Management REST API

A Library Management REST API built with Spring Boot to manage users, books, and loans in a modern, scalable, and secure way.
_Built with Spring Boot 3.5.7, Java 21, Docker, Flyway, and Maven. Uses PostgreSQL (Supabase) for the database and Redis (Upstash)
for caching._

### Core Features

* **Authentication & Security:** Spring Security with stateless JWT implementation. Tokens expire after 1 hour. Role-based access
  control (USER vs ADMIN).
* **Architecture:** Standard Controller-Service-Repository pattern.
* **Data Mapping:** Uses MapStruct and DTOs to ensure requests and responses are structured, type-safe, and high-performance.
* **Error Handling:** GlobalExceptionHandler mapping custom business exceptions to standard HTTP responses.
* **Caching & Performance:** Redis integration to cache heavily accessed endpoints.
* **Database Auditing:** Base entities automatically track `created_at` and `last_modified_at` timestamps.
* **Observability:** Spring Boot Actuator enabled for real-time health checks, metric monitoring, and dynamic log leveling (
  integrated with Better Stack).
* **Documentation:** Auto-generated Swagger UI for endpoint exploration and testing.
* **CI/CD:** Automated testing pipeline using GitHub Actions.

### Business Rules

1. **Availability:** Users cannot borrow a book if there are no available copies. Borrowing reduces copies by 1; returning
   increases copies by 1.
2. **Loan Limits:** A user can have a maximum of 3 active loans at any given time.
3. **Duplicate Loans:** A user cannot borrow multiple copies of the exact same book simultaneously.
4. **Roles & Access:** Users can have either a `USER` or `ADMIN` role. Certain administrative endpoints are restricted to Admins.
5. **Profile Management:** Users can update their personal information and passwords. They can retrieve their own info via
   `/users/me` and `/loans/me`.

### How to Run Locally

Create and configure your `.env` file in the root directory:

```properties
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_DB=
DB_HOST=
DB_PORT=5432
REDIS_HOST=
REDIS_PORT=
JWT_SECRET=
JWT_EXPIRATION=
APP_PORT=8080
ACTUATOR_USERNAME=
ACTUATOR_PASSWORD=
```

Run a PostgreSQL and Redis instance. The provided docker-compose.yml file has everything you need to build them using:
`docker-compose up -d`. Then, run the Spring Boot application locally via your IDE or Maven.

Alternatively, you can run the entire stack (Database, Redis, and Application) in containers with:
`docker-compose --profile prod up --build`.

### Production Deployment

This application is configured for cloud hosting:
-Database: Supabase (PostgreSQL with Transaction Pooler for IPv4 support).

- Cache: Upstash (Serverless Redis).
- Host: Render, using the prod Spring profile.

_Note on Swagger in Production_: To be able to view the API documentation in the production environment, the properties disabling
Swagger in application-prod.properties are temporarily commented out.