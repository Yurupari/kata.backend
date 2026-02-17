# Haiilo Kata

## Project Overview
This project is a Spring Boot application that provides an API for a checkout system.

## Architecture
This project follows a monolithic architecture and is built using the following technologies:
- **Java 21**
- **Spring Boot 4.0.2**
- **Gradle 9.3.0**
- **Docker**
- **H2 Database** (for integration tests)
- **PostgreSQL 18-alpine** (as database)
- **Redis** (for API response caching)

### Caching with Redis
To enhance performance and reduce redundant calls to the GitHub API, the application utilizes Redis for caching.
- **Integration**: Spring's caching abstraction is enabled with `@EnableCaching`, and the `calculatePopularityScore` method is annotated with `@Cacheable("popularity-scores")` to cache its results.
- **Configuration**: Redis connection details are configured in `application.yaml`, and a custom `RedisCacheConfiguration` ensures proper JSON serialization of cached objects.
- **Local Development**: A Redis service is included in `docker-compose.yaml` and `docker-compose-local.yaml`, allowing for easy local setup and testing of the caching mechanism.

## API Documentation
The application exposes the following endpoints:

### Health API Endpoint
`http://localhost:8080/actuator/health`

### Swagger UI / OpenAPI
The application provides an interactive API documentation through Swagger UI. Once the application is running, you can access it at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Spec**: `http://localhost:8080/v3/api-docs`
  ```
  curl --location 'http://localhost:8080/v3/api-docs'

### Error Response Body
The error response body will be a JSON object similar to this structure:
```json
{
    "httpStatus": "BAD_REQUEST",
    "timeStamp": "20251215 09:24:12",
    "message": "ID cannot be null"
}
```

## Deployment
1. Download and install [Git](https://git-scm.com/install/).
2. Download and install [OpenJDK 21](https://adoptopenjdk.net/).
3. Download and install [Gradle](https://gradle.org/install/) using the version [9.3.0](https://gradle.org/releases/#9.3.0) or superior, to avoid possible problems in the building.
4. Download and install [Docker](https://docs.docker.com/engine/install/) and [Docker Compose](https://docs.docker.com/compose/install/).
5. Clone the following repository into your local machine using the following command:
   ```
   git clone https://github.com/Yurupari/kata.backend.git
6. Enter the repository folder, and build the project using the following command:
   ```
   ./gradlew build
7. Add into the `.env` file the values of the environment variables.
8. Run the application with Docker using the following command:
   ```
   docker-compose up -d
   ```
    * NOTE: If you're running on a local machine instead of a server, you can use a personalized configuration through the `docker-compose-local.yaml` file and execute the following command:
      ```
      docker-compose -f docker-compose-local.yaml up -d
    * **Debug Port**: The application exposes a debug port at `8085` for remote debugging.

## Testing
To run the tests and generate a coverage report, execute the following command:
```
./gradlew test --rerun-tasks
```
This command will:
- Run all unit and integration tests.
- Display a summary of test results directly in the console.
- Generate a detailed HTML test report at `build/reports/tests/test/index.html`.
- Generate a detailed HTML code coverage report at `build/reports/jacoco/html/index.html`.
