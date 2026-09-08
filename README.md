# Bashkirceva Spring Project

Spring Boot REST API для управления пользователями и задачами. Проект включает JWT-аутентификацию, роли пользователей, CRUD-операции, мониторинг через Actuator/Prometheus/Grafana, Docker-контейнеризацию и CI/CD через GitHub Actions.

## Стек технологий

- Java 21
- Spring Boot
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- H2 для тестов
- Maven
- Docker и Docker Compose
- Spring Boot Actuator
- Prometheus
- Grafana
- GitHub Actions
- Docker Hub
- Render

## Возможности

- Регистрация пользователя
- Вход в систему с получением JWT-токена
- CRUD для пользователей
- CRUD для задач
- Фильтрация задач по статусу и дедлайну
- Разграничение доступа по ролям `USER` и `ADMIN`
- Валидация входящих DTO через `@Valid`
- Логирование каждого HTTP-запроса
- Глобальная обработка ошибок
- Метрики приложения через `/actuator/prometheus`
- Кастомная метрика неуспешных попыток входа

## Запуск локально

```bash
docker compose up --build
```

После запуска доступны:

- API: `http://localhost:8080`
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000`

Данные для входа в Grafana по умолчанию:

```text
login: admin
password: admin
```

## Переменные окружения

Для production-окружения необходимо задать:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SPRING_DATASOURCE_DRIVER_CLASS_NAME
SPRING_SQL_INIT_MODE
JWT_SECRET
```

`JWT_SECRET` должен быть длинной случайной строкой и не должен храниться в коде.

## Основные endpoint'ы

### Auth

```http
POST /api/auth/register
POST /api/auth/login
```

Пример регистрации:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"user_test","email":"user_test@example.com","password":"password123"}'
```

Пример входа:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user_test","password":"password123"}'
```

### Users

```http
GET /api/v1/users
GET /api/v1/users/{id}
POST /api/v1/users
PUT /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

### Tasks

```http
GET /api/v1/tasks
GET /api/v1/tasks/{id}
POST /api/v1/tasks?userId={userId}
PUT /api/v1/tasks/{id}
DELETE /api/v1/tasks/{id}
```

Фильтрация задач:

```http
GET /api/v1/tasks?status=DONE
GET /api/v1/tasks?deadlineBefore=2026-12-31
GET /api/v1/tasks?from=2026-01-01&to=2026-12-31
```

## Авторизация

Защищённые endpoint'ы вызываются с JWT-токеном:

```bash
curl -i http://localhost:8080/api/v1/users \
  -H "Authorization: Bearer <TOKEN>"
```

Проверка доступа:

- без токена — доступ запрещён;
- с ролью `USER` — доступ только к разрешённым пользовательским операциям;
- с ролью `ADMIN` — доступ к административным endpoint'ам.

## Мониторинг

Actuator endpoint:

```text
http://localhost:8080/actuator/prometheus
```

Prometheus собирает метрики приложения по адресу:

```text
app:8080/actuator/prometheus
```

В Grafana можно использовать Prometheus datasource:

```text
http://prometheus:9090
```

Рекомендуемые панели:

- `Service availability`

```promql
up{job="spring-boot-app"}
```

- `HTTP requests by method/outcome`

```promql
sum by (method, outcome) (http_server_requests_seconds_count{job="spring-boot-app"})
```

- `JVM memory used`

```promql
sum(jvm_memory_used_bytes{job="spring-boot-app"})
```

- `Failed login attempts`

```promql
auth_login_failed_total
```

- `CPU usage`

```promql
process_cpu_usage{job="spring-boot-app"}
```

- `Database active connections`

```promql
hikaricp_connections_active{job="spring-boot-app"}
```

## CI/CD

GitHub Actions выполняет:

- запуск тестов;
- сборку Docker image;
- публикацию Docker image в Docker Hub.

Docker image:

```text
catreshka/bashkirceva-springproject:latest
```

Приложение задеплоено на Render:

```text
https://bashkirceva-springproject.onrender.com
```

## Тесты

Запуск тестов:

```bash
./mvnw test
```

В проекте есть unit и integration tests для auth, user и JWT-логики.
