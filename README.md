# 📑 Manual de Convenciones de Backend en Spring Boot

Este documento define las **mejores prácticas y convenciones** para desarrollar un backend con Spring Boot. Contiene **explicaciones**, **ejemplos de código** y **guías conceptuales para que los equipos puedan empezar a codificar de manera consistente**.

---

**⚠️ NOTA: TODO EL CÓDIGO, COMENTARIOS Y DOCUMENTACIÓN DEBEN ESTAR ESCRITOS EN INGLÉS. ESTO INCLUYE TODAS LAS CLASES, MÉTODOS, VARIABLES, DTOs, EXCEPCIONES, CONTROLLER ADVICE Y CUALQUIER DOCUMENTACIÓN DEL CODIGO. NO SE DEBE USAR ESPAÑOL NI OTRO IDIOMA EN NINGUNA PARTE DEL CÓDIGO O COMENTARIOS. ⚠️**

---

# 📑 Librerías y Dependencias Recomendadas

* **Java 17**: Versión LTS, estable y compatible con Spring Boot 3.5.5.
* **Spring Boot 3.5.5**: Framework para desarrollo de aplicaciones Java, con soporte completo para REST, seguridad, testing y más.
* **Gradle**: Sistema de construcción moderno que utiliza Groovy/Kotlin DSL para la configuración del proyecto.

**Librerías principales y su uso:**

* **Spring Web**
  Proporciona funcionalidades para crear aplicaciones web, incluyendo soporte para **MVC (Model-View-Controller)** y **APIs RESTful**. Permite manejar rutas, controladores y respuestas HTTP de manera sencilla y estandarizada.

* **Spring Data JPA**
  Facilita la implementación de **repositorios basados en JPA**, simplificando el acceso a bases de datos relacionales. Permite definir interfaces de repositorio que Spring implementa automáticamente, y manejar entidades con operaciones CRUD sin escribir SQL manualmente.

* **Lombok**
  Biblioteca que reduce el **código repetitivo** en Java, como getters, setters, constructores, `toString`, `equals` y `hashCode`. Genera automáticamente este código en tiempo de compilación, haciendo las clases más limpias y legibles.

* **Spring Boot DevTools**
  Herramienta de desarrollo que acelera el ciclo de desarrollo ofreciendo **recarga automática** al detectar cambios en el código, reinicio rápido de la aplicación y configuraciones simplificadas para el entorno local.

* **PostgreSQL Driver**
  Dependencia necesaria para que la aplicación pueda interactuar con **bases de datos PostgreSQL**. Proporciona el controlador JDBC que permite a Spring Data JPA conectarse, ejecutar consultas y manejar transacciones con PostgreSQL.

---

## 📂 1. Estructura de Carpetas

**Explicación:**
La estructura de carpetas organiza el proyecto para mantenerlo **ordenado y escalable**. Cada carpeta tiene un propósito claro.

| Carpeta        | Propósito                                              | Ejemplo / Nota                                                  |
| -------------- | ------------------------------------------------------ | --------------------------------------------------------------- |
| `config/`      | Configuraciones globales como seguridad, CORS, Swagger | `SecurityConfig.java`, `SwaggerConfig.java`                     |
| `controller/`  | Controladores REST que exponen los endpoints           | `UserController.java`                                           |
| `dto/`         | Objetos de transferencia de datos (Request / Response) | `UserRequest.java`, `UserResponse.java`, `RestResponse.java`    |
| `exception/`   | Manejo centralizado de errores                         | `GlobalExceptionHandler.java`, `ResourceNotFoundException.java` |
| `mapper/`      | Conversión entre DTO y Entity                          | `UserMapper.java`                                               |
| `model/`       | Entidades JPA que representan tablas                   | `User.java`                                                     |
| `repository/`  | Interfaces para acceso a datos                         | `UserRepository.java`                                           |
| `service/`     | Lógica de negocio                                      | `UserService.java` (interfaces)                                 |
| `service/impl/` | Implementaciones de servicios                          | `UserServiceImpl.java`                                          |
| `util/`        | Clases de utilidad o helpers                           | `DateUtils.java`                                                |

**Ejemplo conceptual:**

```text
src/main/java/com/curso/proyecto/
 ├── controller/
 ├── dto/
 ├── exception/
 ├── mapper/
 ├── model/
 ├── repository/
 ├── service/
 │    └── impl/
 ├── config/
 └── util/
```

---

## 🔹 2. Nomenclatura

**Explicación:**
Seguir un estándar de nombres ayuda a mantener el proyecto **legible y consistente** para todo el equipo.

| Elemento            | Convención                | Ejemplo                             |
| ------------------- | ------------------------- | ----------------------------------- |
| Paquetes            | minúsculas                | `com.curso.proyecto.controller`     |
| Clases              | PascalCase                | `UserServiceImpl`                   |
| Métodos y variables | camelCase                 | `getActiveUsers()`                  |
| Constantes          | MAYÚSCULAS con `_`        | `MAX_RETRY_ATTEMPTS`                |
| DTOs                | Sufijo Request / Response | `UserRequest`, `UserResponse`       |
| Repositorios        | Sufijo Repository         | `UserRepository`                    |
| Servicios           | Sufijo Service            | `UserService` (interfaces)          |

**Ejemplo conceptual:**

```java
// Correcto
public class UserServiceImpl { ... }
private String username;
public static final int MAX_RETRY_ATTEMPTS = 3;

// Incorrecto
public class userSERVICE { ... }
private String User_Name;
```

---

## 🔹 3. Endpoints REST

**Explicación:**

* Usar **verbos HTTP correctos** (`GET`, `POST`, `PUT`, `DELETE`).
* Prefijo de API: `/api/v1/`.
* Usar **plural en inglés** para recursos.
* ❌ No usar verbos en las URLs (`/createUser`).

**Ejemplo conceptual:**

```http
GET    /api/v1/users        → listar todos los usuarios
GET    /api/v1/users/{id}   → obtener un usuario por ID
POST   /api/v1/users        → crear un usuario
PUT    /api/v1/users/{id}   → actualizar un usuario
DELETE /api/v1/users/{id}   → eliminar un usuario
```

---

## 🔹 4. Documentación con Swagger

**Explicación:**

* Swagger permite **documentar automáticamente los endpoints**.
* Anotar los métodos con `@Operation` y `@ApiResponses`.

**Ejemplo conceptual:**

```java
@Operation(summary = "Get user by ID", description = "Returns a user by their ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found"),
    @ApiResponse(responseCode = "404", description = "User not found")
})
@GetMapping("/{id}")
public RestResponse<UserResponse> getUser(@PathVariable Long id) {
    return null; // Ejemplo conceptual
}
```

---

## 🔹 5. DTOs (Data Transfer Objects)

**Explicación:**

* Nunca exponer entidades JPA directamente.
* Usar DTOs para separar **modelo de base de datos** de **entrada/salida de API**.

**Ejemplo conceptual:**

```java
public class UserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}

public class UserResponse {
    private Long id;
    private String name;
    private String email;
}
```

---

## 🔹 6. Validaciones

**Explicación:**

* Validar datos de entrada con **Jakarta Validation** (`@NotBlank`, `@Email`, `@Size`, etc.).
* Aplicar `@Valid` en los controladores.

**Ejemplo conceptual:**

```java
@PostMapping
public RestResponse<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
    return RestResponse.success("User created successfully", null);
}
```

---

## 🔹 7. Mapper (MapStruct)

**Explicación:**

* MapStruct permite **convertir entre Entity ↔ DTO automáticamente**, reduciendo código repetitivo.
* Recomendaciones para el equipo:

1️⃣ **Nombres coherentes**

* Los campos de DTO y entidad deben llamarse igual (`firstName ↔ firstName`).
* Evitar traducciones o nombres distintos si no es estrictamente necesario.
* Esto reduce la necesidad de `@Mapping` manual.

2️⃣ **Tipos compatibles**

* Mantener tipos compatibles (`String ↔ String`, `int ↔ Integer`, `List<User> ↔ List<UserDTO>`).
* Para conversiones complejas (`String ↔ LocalDate`) crear métodos personalizados dentro del mapper.

**Ejemplo conceptual:**

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest dto);
    UserResponse toResponse(User entity);

    // Ejemplo de método personalizado
    default LocalDate mapStringToDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
}
```

---

## 🔹 8. Servicios

**Explicación:**

* Siempre crear **interfaces** para la lógica de negocio.
* Implementar las interfaces en `service/impl/`.
* Los controladores deben depender de la **interfaz**, no de la implementación.

**Ejemplo conceptual:**

```java
// Interface
public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
}

// Implementación
@Service
public class UserServiceImpl implements UserService {
    // Inyecciones de repositorio y mapper
}
```

---

## 🔹 9. Controladores

**Explicación:**

* Solo reciben y devuelven DTOs.
* ❌ No acceder al repositorio directamente ni usar entidades JPA.

**Ejemplo conceptual:**

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public RestResponse<List<UserResponse>> getAllUsers() {
        return RestResponse.success("Users listed successfully", null);
    }
}
```

---

## 🔹 10. Manejo de Errores con RestResponse (RestResponse va en DTO)

**Explicación:**

* Usar `@RestControllerAdvice` para centralizar errores.
* Generar **respuestas uniformes** con `RestResponse`.
* **Flujo completo:**
  1️⃣ Servicio lanza excepción.
  2️⃣ Controlador invoca servicio y retorna RestResponse.
  3️⃣ `@ControllerAdvice` intercepta excepción y devuelve respuesta uniforme.

**Ejemplo conceptual:**

```java
// Excepción personalizada
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

// Manejo centralizado usando RestResponse
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja excepciones de negocio personalizadas
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestResponse<?>> handleUserNotFound(UserNotFoundException ex) {
        RestResponse<?> response = RestResponse.error("Business error", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Maneja validaciones de DTO (ej. @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();

        RestResponse<?> response = RestResponse.error("Validation errors", errors);
        return ResponseEntity.badRequest().body(response);
    }

    // Maneja cualquier excepción no prevista
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<?>> handleGenericException(Exception ex) {
        RestResponse<?> response = RestResponse.error("Internal server error", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
```

**Flujo conceptual:**

```java
// Servicio
throw new UserNotFoundException("User with id 123 not found");

// Controlador
@GetMapping("/users/{id}")
public RestResponse<User> getUser(@PathVariable Long id) {
    User user = userService.getUserById(id);
    return RestResponse.success("User found", user);
}

// ControllerAdvice intercepta y devuelve
{
  "success": false,
  "message": "Business error",
  "data": null,
  "errors": ["User with id 123 not found"]
}
```

---

## 🔹 11. API Estándar (Envelope de Respuesta) (RestResponse va en DTO)

**Explicación:**

* Todos los endpoints deben devolver un **envelope uniforme**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "errors": [ ... ]
}
```

**Ejemplo conceptual en Java:**

```java
public class RestResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;

    public static <T> RestResponse<T> success(String message, T data) {
        RestResponse<T> response = new RestResponse<>();
        response.success = true;
        response.message = message;
        response.data = data;
        response.errors = List.of();
        return response;
    }

    public static <T> RestResponse<T> error(String message, List<String> errors) {
        RestResponse<T> response = new RestResponse<>();
        response.success = false;
        response.message = message;
        response.data = null;
        response.errors = errors;
        return response;
    }

    // Getters y setters omitidos para brevedad
}
```

---

## 🔹 12. Estrategia de Testing

**Explicación:**

* Tests unitarios, de integración y end-to-end.
* Naming estándar para claridad:

✅ Naming correcto:

```java
shouldReturnUserWhenUserExists()
shouldThrowExceptionWhenUserNotFound()
```

❌ Naming incorrecto:

```java
test1()
userTest()
```

**Ejemplo conceptual:**

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository repository;
    @InjectMocks private UserServiceImpl service;

    @Test
    void shouldReturnUserWhenUserExists() {
        // Setup conceptual
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Setup conceptual
    }
}
```

---

## 🔹 13. Variables de Entorno

**Explicación:**

* No hardcodear claves, contraseñas o tokens.
* Usar **variables de entorno** o `application.properties`.

**Ejemplo conceptual:**

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
jwt.secret=${JWT_SECRET}
```

```bash
export DB_URL=jdbc:postgresql://localhost:5432/curso
export DB_USER=postgres
export DB_PASS=1234
export JWT_SECRET=mi_secreto
```

---

## 🔹 14. Gradle Configuration

**Explicación:**

* Usar Gradle como sistema de construcción para gestionar dependencias y tareas del proyecto.
* Configuración en `build.gradle` para Spring Boot con las librerías recomendadas.

**Ejemplo conceptual de build.gradle:**

```gradle
plugins {
    id 'org.springframework.boot' version '3.5.5'
    id 'io.spring.dependency-management' version '1.1.0'
    id 'java'
}

group = 'com.curso'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '17'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    
    compileOnly 'org.projectlombok:lombok'
    
    runtimeOnly 'org.postgresql:postgresql'
    
    annotationProcessor 'org.projectlombok:lombok'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
    
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

**Comandos Gradle útiles:**

```bash
./gradlew build          # Construir el proyecto
./gradlew bootRun        # Ejecutar la aplicación
./gradlew test           # Ejecutar tests
./gradlew clean          # Limpiar archivos generados
```

---

## 🔹 15. Commits

**Explicación:**

* Convención de commits para mantener claridad en el historial del equipo. Usar **verbos en infinitivo en español** y ser concisos pero claros:

```text
feat: agregar mapper para convertir User a UserDTO
fix: corregir mapeo de fecha en UserMapper
docs: documentar uso de MapStruct en README
test: añadir pruebas unitarias para UserMapper
refactor: renombrar campo 'nombreUsuario' a 'username' en DTO
chore: actualizar dependencias de MapStruct
style: ajustar formato de los mappers según convención del equipo
```

**Recomendaciones:**

* `feat`: nuevas funcionalidades o mapeos.
* `fix`: corrección de errores en mappers o DTOs.
* `docs`: documentación sobre mapeo o uso de DTOs/entidades.
* `test`: pruebas unitarias o de integración de mappers.
* `refactor`: cambios de estructura de código sin alterar funcionalidad.
* `chore`: tareas de mantenimiento, dependencias o configuración.
* `style`: cambios de estilo que no afectan la lógica.

---

## 🔹 16. Pull Request

### 🔹 Plantilla de Pull Request (PR)

**Título del PR:**

```text
<tipo>: <descripción breve de la funcionalidad o cambio>
```

**Tipos recomendados:**

* `feat`: nueva funcionalidad
* `fix`: corrección de error
* `docs`: documentación
* `test`: pruebas
* `refactor`: refactorización
* `chore`: tareas de mantenimiento

**Ejemplo de título:**

```text
feat: agregar mapper para convertir User a UserDTO
```

---

**Descripción del PR:**

1. **Resumen del cambio:**

```text
<Breve explicación de lo que hace este PR y por qué es necesario>
```

2. **Cambios realizados:**

```text
- <Archivo1>: <descripción del cambio>
- <Archivo2>: <descripción del cambio>
- <Archivo3>: <descripción del cambio>
```

3. **Cómo probarlo:**

```text
- <Instrucciones para ejecutar pruebas unitarias>
- <Instrucciones para probar la funcionalidad manualmente>
```

4. **Notas adicionales:**

```text
- <Notas sobre migraciones, dependencias, PRs relacionados, etc.>
```

## 🔹 17. Ramas (Branching Strategy)

**Explicación:**  
El manejo de ramas debe ser **claro, consistente y alineado con la convención de commits**.  
Los nombres de ramas usarán los mismos **prefijos** que los commits (`feat`, `fix`, `docs`, `test`, `refactor`, `chore`, `style`), seguidos de una descripción en **kebab-case**.

---

### 🌱 Tipos de ramas:

- **`main`**  
  Rama estable y en producción. Solo recibe merges desde `dev` o ramas de `hotfix/*`.

- **`dev`**  
  Rama de integración donde se juntan todas las funcionalidades antes de pasar a producción.

- **`feat/*`**  
  Para nuevas funcionalidades. Siempre parte de `dev`.  
  Ejemplo: `feat/user-authentication`

- **`fix/*`**  
  Para corrección de errores encontrados en `dev`.  
  Ejemplo: `fix/login-validation`

- **`hotfix/*`**  
  Para arreglos urgentes en producción (`main`).  
  Ejemplo: `hotfix/nullpointer-on-order`

- **`docs/*`**  
  Para cambios o mejoras en la documentación.  
  Ejemplo: `docs/update-readme`

- **`test/*`**  
  Para creación o ajuste de pruebas unitarias/integración.  
  Ejemplo: `test/user-service-unit`

- **`refactor/*`**  
  Para cambios de estructura interna sin alterar la lógica.  
  Ejemplo: `refactor/rename-user-fields`

- **`chore/*`**  
  Para tareas de mantenimiento o configuraciones.  
  Ejemplo: `chore/update-dependencies`

- **`style/*`**  
  Para ajustes de formato y estilo (sin cambios en la lógica).  
  Ejemplo: `style/format-controllers`

---

### 🔧 Flujo recomendado:

1. Crear rama desde `dev`:
   ```bash
   git checkout dev
   git pull origin dev
   git checkout -b feat/user-authentication
   ```

2. Hacer commits siguiendo la convención:
```bash
feat: agregar autenticación de usuarios con JWT
```

3. Subir la rama:

```bash
git push origin feat/user-authentication
```

4. Crear Pull Request hacia dev.

5. Tras aprobar el PR, hacer merge y eliminar la rama:

```bash
git branch -d feat/user-authentication
git push origin --delete feat/user-authentication
```

## 📌 Buenas prácticas:

Un cambio → una rama.
Evitar mezclar varias cosas en la misma rama.

Nombres en inglés y kebab-case.
Ej: feat/add-user-endpoint, fix/date-mapper.

Actualizar antes de empezar a trabajar:
```bash
git checkout dev
git pull origin dev
```

Nunca trabajar directo en main.

---

**Checklist recomendado:**

* [ ] Código probado y compilado correctamente
* [ ] Pruebas unitarias agregadas o actualizadas
* [ ] Documentación actualizada (si aplica)
* [ ] Revisado por al menos un compañero del equipo
