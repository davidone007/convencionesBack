package co.edu.icesi.planeacionpostgrados.exception;

/**
 * Exception thrown when a user is not found in the system.
 * This is a business logic exception that should be handled by the GlobalExceptionHandler.
 */
public class UserNotFoundException extends BusinessException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UserNotFoundException(Long userId) {
        super("User with ID " + userId + " not found");
    }
}
