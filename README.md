# msvc-users

Microservicio de **gestión de usuarios**. Expone API versionada (v1/v2), persiste en MySQL y ofrece un endpoint interno de autenticación consumido por **oauth**.

## Stack

- Java 21 · Spring Boot 4.1.0 · Spring Cloud 2025.1.2
- Puerto: **dinámico** (`${PORT:0}`) — consultar instancia en Eureka
- Base de datos: MySQL `db_springboot_cloud`
- **Distributed tracing:** [Micrometer Tracing](https://docs.micrometer.io/tracing/reference/) + Zipkin. Ver [docs/TRACING.md](docs/TRACING.md)

## Endpoints

### API v1 — base `/v1`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/v1` | Lista usuarios (entidad completa) |
| GET | `/v1/{id}` | Usuario por ID |
| POST | `/v1` | Crea usuario |
| PUT | `/v1/{id}` | Actualiza usuario |
| DELETE | `/v1/{id}` | Elimina usuario |

### API v2 — base `/v2`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/v2` | Lista usuarios (DTO) |
| GET | `/v2/{id}` | Usuario por ID (DTO) |
| POST | `/v2` | Crea usuario |
| PUT | `/v2/{id}` | Actualiza usuario |
| DELETE | `/v2/{id}` | Elimina usuario |

### Interno — base `/internal/auth` (requiere header `X-Internal-Token`)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/internal/auth/users/{username}` | Datos de autenticación para OAuth (hash, roles, enabled) |

### Vía API Gateway (puerto 8080)

Prefijo: `/api/users/**` (StripPrefix=2)

| Método | Ruta Gateway | Equivale en servicio |
|--------|--------------|----------------------|
| GET | `/api/users/v1` | `GET /v1` |
| GET | `/api/users/v1/{id}` | `GET /v1/{id}` |
| POST | `/api/users/v1` | `POST /v1` |
| PUT | `/api/users/v1/{id}` | `PUT /v1/{id}` |
| DELETE | `/api/users/v1/{id}` | `DELETE /v1/{id}` |
| * | `/api/users/v2/**` | Equivalente en `/v2/**` |

**Fallback gateway:** `/fallback/users`

## Importancia en el ecosistema

Microservicio de **identidad y usuarios**. Es la fuente de credenciales y roles para el flujo de autenticación.

**Dependencias:** Eureka, MySQL.

**Consumido por:** **oauth** (Feign → `/internal/auth`), **msvc-gateway-server** (proxy), **flutter_spring_boot** (indirectamente vía gateway).

**Orden de arranque recomendado:** 3.º, antes de **oauth** y del gateway.

## Tracing (Zipkin)

```bash
cd .. && docker compose up -d   # raíz SpringCloud → http://localhost:9411
```

Detalle: [docs/TRACING.md](docs/TRACING.md).
