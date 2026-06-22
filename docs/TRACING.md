# Distributed Tracing en msvc-users

> **Guía completa:** [docs/ZIPKIN.md](../../docs/ZIPKIN.md)

[Micrometer Tracing](https://docs.micrometer.io/tracing/reference/) + **Brave** + **Zipkin**.

| Componente | Span automático |
|------------|-----------------|
| HTTP entrante (`/v1/**`, `/v2/**`, `/internal/auth/**`) | Sí |
| JPA / MySQL | Parcial (via Observation) |
| Logs | `[msvc-users,traceId,spanId]` |

## Flujo típico

```
oauth (Feign) → msvc-users /internal/auth/users/{username}
gateway       → msvc-users /v1, /v2
```

## Zipkin (local)

Desde la raíz `SpringCloud/`:

```bash
docker compose up -d
```

## Referencias

- [Spring Boot — Tracing](https://docs.spring.io/spring-boot/reference/actuator/tracing.html)
