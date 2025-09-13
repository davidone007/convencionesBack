package co.edu.icesi.planeacionpostgrados.exception;

import co.edu.icesi.planeacionpostgrados.dto.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * Global exception handler that catches and handles all exceptions in the application.
 * Provides uniform error responses using RestResponse envelope pattern.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException and returns 404 Not Found response
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestResponse<?>> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        log.warn("User not found exception: {}", ex.getMessage());
        
        RestResponse<?> response = RestResponse.error("User not found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles NoHandlerFoundException for 404 errors when endpoint is not found
     * Returns 404 Not Found response instead of Internal Server Error
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestResponse<?>> handleNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
        log.warn("No handler found for {} {}", ex.getHttpMethod(), ex.getRequestURL());
        
        RestResponse<?> response = RestResponse.error(
            "Endpoint not found", 
            "The requested endpoint " + ex.getHttpMethod() + " " + ex.getRequestURL() + " was not found"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles NoResourceFoundException for 404 errors when resource is not found (Spring Boot 3)
     * Returns 404 Not Found response instead of Internal Server Error
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestResponse<?>> handleNoResourceFound(NoResourceFoundException ex, WebRequest request) {
        log.warn("No resource found for {}", ex.getResourcePath());
        
        RestResponse<?> response = RestResponse.error(
            "Resource not found", 
            "The requested resource " + ex.getResourcePath() + " was not found"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles AccessDeniedException for security-related access denials
     * Returns 404 Not Found instead of 403 Forbidden to avoid revealing endpoint existence
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestResponse<?>> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        log.warn("Access denied: {}", ex.getMessage());
        
        // Return 404 instead of 403 to not reveal if endpoint exists
        RestResponse<?> response = RestResponse.error(
            "Endpoint not found", 
            "The requested endpoint was not found"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles all business logic exceptions and returns 400 Bad Request response
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<RestResponse<?>> handleBusinessException(BusinessException ex, WebRequest request) {
        log.warn("Business exception: {}", ex.getMessage());
        
        RestResponse<?> response = RestResponse.error("Business error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles validation errors from @Valid annotations
     * Returns 400 Bad Request with detailed field validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        RestResponse<?> response = RestResponse.error("Validation errors", errors);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles IllegalArgumentException and returns 400 Bad Request response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.warn("Illegal argument exception: {}", ex.getMessage());
        
        RestResponse<?> response = RestResponse.error("Invalid argument", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles any unhandled exceptions and returns 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<?>> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        
        RestResponse<?> response = RestResponse.error(
            "Internal server error", 
            "An unexpected error occurred. Please contact support if the problem persists."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handles RuntimeException and returns 500 Internal Server Error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestResponse<?>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("Runtime exception occurred: {}", ex.getMessage(), ex);
        
        RestResponse<?> response = RestResponse.error("Runtime error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
