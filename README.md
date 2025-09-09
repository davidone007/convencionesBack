# 📑 Manual de Convenciones de Backend en Spring Boot

Este documento define las **mejores prácticas y convenciones** para desarrollar un backend con Spring Boot. Contiene **explicaciones**, **ejemplos de código** y **guías conceptuales para que los equipos puedan empezar a codificar de manera consistente**.

---

# 📑 Librerías y Dependencias Recomendadas

* **Java 17**: Versión LTS, estable y compatible con Spring Boot 3.5.5.
* **Spring Boot 3.5.5**: Framework para desarrollo de aplicaciones Java, con soporte completo para REST, seguridad, testing y más.

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

| Carpeta                   | Propósito                                              | Ejemplo / Nota                                                  |
| ------------------------- | ------------------------------------------------------ | --------------------------------------------------------------- |
| `config/`                 | Configuraciones globales como seguridad, CORS, Swagger | `SecurityConfig.java`, `SwaggerConfig.java`                     |
| `controller/`             | Controladores REST que exponen los endpoints           | `UsuarioController.java`                                        |
| `dto/`                    | Objetos de transferencia de datos (Request / Response) | `UsuarioRequest.java`, `UsuarioResponse.java`                   |
| `exception/`              | Manejo centralizado de errores                         | `GlobalExceptionHandler.java`, `ResourceNotFoundException.java` |
| `mapper/`                 | Conversión entre DTO y Entity                          | `UsuarioMapper.java`                                            |
| `persistence/model/`      | Entidades JPA que representan tablas                   | `Usuario.java`                                                  |
| `persistence/repository/` | Interfaces para acceso a datos                         | `UsuarioRepository.java`                                        |
| `service/`                | Lógica de negocio                                      | `UsuarioService.java` (interfaces)                              |
| `service/impl/`           | Implementaciones de servicios                          | `UsuarioServiceImpl.java`                                       |
| `util/`                   | Clases de utilidad o helpers                           | `DateUtils.java`                                                |

**Ejemplo conceptual:**

```text
src/main/java/com/curso/proyecto/
 ├── controller/
 ├── dto/
 ├── exception/
 ├── mapper/
 ├── persistence/
 │    ├── model/
 │    └── repository/
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
| Clases              | PascalCase                | `UsuarioServiceImpl`                |
| Métodos y variables | camelCase                 | `getUsuariosActivos()`              |
| Constantes          | MAYÚSCULAS con `_`        | `MAX_REINTENTOS`                    |
| DTOs                | Sufijo Request / Response | `UsuarioRequest`, `UsuarioResponse` |
| Repositorios        | Sufijo Repository         | `UsuarioRepository`                 |
| Servicios           | Sufijo Service            | `UsuarioService` (interfaces)       |

**Ejemplo conceptual:**

```java
// Correcto
public class UsuarioServiceImpl { ... }
private String nombreUsuario;
public static final int MAX_REINTENTOS = 3;

// Incorrecto
public class usuarioSERVICE { ... }
private String Nombre_Usuario;
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
@Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario dado su ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
})
@GetMapping("/{id}")
public UsuarioResponse obtener(@PathVariable Long id) {
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
public class UsuarioRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Email inválido")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}

public class UsuarioResponse {
    private Long id;
    private String nombre;
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
public ApiResponse<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
    return ApiResponse.success("Usuario creado correctamente", null);
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
public interface UsuarioMapper {
    Usuario toEntity(UsuarioRequest dto);
    UsuarioResponse toResponse(Usuario entity);

    // Ejemplo de método personalizado
    default LocalDate mapStringToDate(String fecha) {
        return LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE);
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
public interface UsuarioService {
    UsuarioResponse crear(UsuarioRequest request);
    List<UsuarioResponse> listar();
}

// Implementación
@Service
public class UsuarioServiceImpl implements UsuarioService {
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
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ApiResponse<List<UsuarioResponse>> listar() {
        return ApiResponse.success("Usuarios listados", null);
    }
}
```

---

## 🔹 10. Manejo de Errores con ApiResponse (ApiResponse va en DTO)

**Explicación:**

* Usar `@RestControllerAdvice` para centralizar errores.
* Generar **respuestas uniformes** con `ApiResponse`.
* **Flujo completo:**
  1️⃣ Servicio lanza excepción.
  2️⃣ Controlador invoca servicio y retorna ApiResponse.
  3️⃣ `@ControllerAdvice` intercepta excepción y devuelve respuesta uniforme.

**Ejemplo conceptual:**

```java
// Excepción personalizada
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

// Manejo centralizado usando ApiResponse
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja excepciones de negocio personalizadas
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(UserNotFoundException ex) {
        ApiResponse<?> response = ApiResponse.error("Error de negocio", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Maneja validaciones de DTO (ej. @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();

        ApiResponse<?> response = ApiResponse.error("Errores de validación", errors);
        return ResponseEntity.badRequest().body(response);
    }

    // Maneja cualquier excepción no prevista
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ApiResponse<?> response = ApiResponse.error("Error interno del servidor", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
```

**Flujo conceptual:**

```java
// Servicio
throw new UserNotFoundException("Usuario con id 123 no encontrado");

// Controlador
@GetMapping("/users/{id}")
public ApiResponse<User> getUser(@PathVariable Long id) {
    User user = userService.getUserById(id);
    return ApiResponse.success("Usuario encontrado", user);
}

// ControllerAdvice intercepta y devuelve
{
  "success": false,
  "message":
  "message": "Error de negocio",
  "data": null,
  "errors": ["Usuario con id 123 no encontrado"]
}
```

---

## 🔹 11. API Estándar (Envelope de Respuesta) (ApiResponse va en DTO)

**Explicación:**

* Todos los endpoints deben devolver un **envelope uniforme**:

```json
{
  "success": true,
  "message": "Operación exitosa",
  "data": { ... },
  "errors": [ ... ]
}
```

**Ejemplo conceptual en Java:**

```java
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.message = message;
        response.data = data;
        response.errors = List.of();
        return response;
    }

    public static <T> ApiResponse<T> error(String message, List<String> errors) {
        ApiResponse<T> response = new ApiResponse<>();
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
usuarioTest()
```

**Ejemplo conceptual:**

```java
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock private UsuarioRepository repository;
    @InjectMocks private UsuarioServiceImpl service;

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

## 🔹 14. Commits

**Explicación:**

* Convención de commits para mantener claridad en el historial del equipo:

```text
feat: agregar endpoint de creación de usuario
fix: corregir validación de email
docs: documentar API con Swagger
test: añadir pruebas unitarias para UsuarioService
```

---




