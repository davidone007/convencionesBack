package co.edu.icesi.planeacionpostgrados.exception;

/**
 * Base class for all business logic exceptions in the application.
 * This allows for better exception hierarchy and handling.
 */
public abstract class BusinessException extends RuntimeException {
    
    protected BusinessException(String message) {
        super(message);
    }
    
    protected BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
