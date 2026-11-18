# OrderIt API

REST backend for [OrderIt](https://github.com/LennyRBriones/orderit-android), a tablet app that restaurant waiters use to take and manage orders. It serves waiters, the menu, dining tables and orders with their line items, and runs on Google Cloud Run.

## Stack

- **Kotlin** with **Ktor** for routing, content negotiation, status pages and CORS
- **Exposed** for database access
- **kotlinx.serialization** for JSON
- **Docker** image, deployed to **Google Cloud Run**

## Structure

```
src/main/kotlin/
├── Application.kt, Routing.kt, HTTP.kt, Serialization.kt   # Ktor setup
└── com/orderit/
    ├── DatabaseFactory.kt   # connection and schema
    ├── models/              # Exposed tables
    ├── dtos/                # request and response bodies
    ├── services/            # business logic per resource
    └── routes/              # HTTP routes per resource
```

## Resources

`/waiters`, `/categories`, `/products`, `/tables`, `/orders`, `/orders/{id}/details`

Every endpoint, with request and response examples, is documented in [API_DOCS.md](API_DOCS.md).

## Running locally

```bash
./gradlew run          # starts the server on http://0.0.0.0:8080
./gradlew test         # runs the tests
./gradlew buildFatJar  # builds a runnable JAR
./gradlew runDocker    # runs it from the local Docker image
```
