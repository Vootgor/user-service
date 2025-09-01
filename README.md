# User Service

**User Service** — микросервис проекта BikeBuilder для управления пользователями,
включая регистрацию, просмотр, обновление и удаление.

## Основные возможности

- Создание нового пользователя
- Получение информации о пользователе
- Обновление данных пользователя
- Удаление пользователя
- Отправка событий через Kafka при изменениях пользователей

---

## Архитектура

- Hexagonal Architecture (Ports & Adapters)
- Event-driven через Kafka для обмена событиями между микросервисами
- PostgreSQL для хранения данных
- Gradle для сборки проекта
- Docker для контейнеризации

---

## Структура проекта

```
src/
├─ main/
│ ├─ java/com/bikebuilder/userservice/
│ │ ├─ adapter/in/web/ # REST контроллеры и DTO
│ │ ├─ adapter/out/persistence/ # Адаптеры для работы с БД (Entity, Repository, Adapter)
│ │ ├─ adapter/out/messaging/ # Публикация событий
│ │ ├─ application/port/ # Порты для Hexagonal архитектуры
│ │ ├─ application/usecase/ # Реализация бизнес-логики
│ │ ├─ config/ # Конфигурации Kafka
│ │ └─ domain/ # Доменные модели (User) и enum (Role)
│ └─ resources/
│ ├─ application.yaml
│ └─ db/changelog/ # Миграции для базы данных
└─ test/ # Unit и Integration тесты
```

---

## Сборка и запуск

### Локально

```bash
./gradlew build
./gradlew bootRun
```

### С Docker

```bash
docker-compose up -d
```
- Контейнер поднимает Zookeeper, Kafdrop, Kafka и PostgreSQL

---

## Технологии

- Java 24
- Spring Boot 3.5.4
- PostgreSQL 16
- Kafka
- Gradle
- JUnit 5, Mockito

---

## API

### Создание пользователя

**POST** `/api/users/`

**Request Body**

```json
{
  "email": "ser@example.com",
  "password": "newPassword123"
}
```

**Response Body**

```json
{
  "id": "uuid",
  "email": "ser@example.com",
  "name": null,
  "lastName": null,
  "phoneNumber": null,
  "role": "ADMIN",
  "created": "2025-09-01T14:22:42.134311700Z",
  "emailVerified": false,
  "isActive": true
}
```

### Получение пользователя по ID

**GET** `/api/users/{id}`

**Response Body**

```json
{
  "id": "uuid",
  "email": "ser@example.com",
  "name": null,
  "lastName": null,
  "phoneNumber": null,
  "role": "ADMIN",
  "created": "2025-09-01T14:22:42.134311700Z",
  "emailVerified": false,
  "isActive": true
}
```

### Обновление данных пользователя

**PUT** `/api/users/{id}`

**Request Body**

```json
{
  "email": "sser@example.com",
  "password": "qwerty",
  "name": "Don",
  "lastName": "Ivanovich",
  "phoneNumber": "8(800)-200-06-00"
}
```

**Response Body**

```json
{
  "id": "uuid",
  "email": "sser@example.com",
  "name": "Don",
  "lastName": "Ivanovich",
  "phoneNumber": "8(800)-200-06-00",
  "role": "ADMIN",
  "created": "2025-08-08T13:38:32.739524Z",
  "emailVerified": false,
  "isActive": true
}
```

### Удаление пользователя по ID

**DELETE** `/api/users/{id}`

**Response Body**

```json
{
  "id": "uuid",
  "email": "ser@example.com",
  "name": null,
  "lastName": null,
  "phoneNumber": null,
  "role": "ADMIN",
  "created": "2025-09-01T14:22:42.134311700Z",
  "emailVerified": false,
  "isActive": true
}
```

---

## Тесты

- Unit тесты: src/test/java/com/bikebuilder/userservice/...
- Integration тесты: src/test/java/com/bikebuilder/userservice/integration

***Запуск***

```bash
./gradlew test
```

---

## Миграции базы данных

- Используется Liquibase через YAML миграции: src/main/resources/db/changelog/
- Пример: 010-init-db-schema.yaml

## TODO

- Метод удаление поменять логику - вместо удаления ставить флаг isActive = false.
- В будущем вынести Zookeeper, Kafdrop, Kafka в др. контейнер.
- При создании пользователя поменять роль.
- При создании мс корзины, брать из неё временный id и присваивать покупателю.