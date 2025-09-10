# 🐳 Docker & PostgreSQL Quick Start

## 1. Levantar PostgreSQL con Docker

```sh
docker run --name demo-postgres -e POSTGRES_DB=demo_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16
```

- Crea un contenedor llamado `demo-postgres` con la base de datos, usuario y contraseña listos.

## 2. Levantar PgAdmin con Docker

```sh
docker run --name demo-pgadmin -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -p 5050:80 -d dpage/pgadmin4
```

- Accede a PgAdmin en: http://localhost:5050
- Usuario: `admin@admin.com` | Contraseña: `admin`

## 3. Conectar PgAdmin a PostgreSQL

- Host: `host.docker.internal` (Windows/Mac) o `localhost`
- Puerto: `5432`
- Usuario: `postgres`
- Contraseña: `postgres`
- Base de datos: `demo_db`

## 4. Poblar la base de datos

```sh
# Desde la carpeta demo donde está populate_users.sql
psql -U postgres -d demo_db -h localhost -f populate_users.sql
```

## 5. Correr la aplicación Spring Boot

```sh
# Desde la carpeta demo
mvnw spring-boot:run
# o si tienes Maven global
mvn spring-boot:run
```

## 6. Acceder a la API y Swagger

- API: http://localhost:8080/api/v1/users
- Swagger: http://localhost:8080/swagger-ui.html o http://localhost:8080/swagger-ui/index.html

---

## 7. Comandos útiles de Docker

- Ver contenedores activos:
  ```sh
  docker ps
  ```
- Detener un contenedor:
  ```sh
  docker stop demo-postgres demo-pgadmin
  ```
- Eliminar un contenedor:
  ```sh
  docker rm demo-postgres demo-pgadmin
  ```
- Ver logs de un contenedor:
  ```sh
  docker logs demo-postgres
  ```
