package co.edu.icesi.planeacionpostgrados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Standard REST API response envelope following project conventions.
 * Provides uniform response structure for all API endpoints.
 * 
 * @param <T> The type of data being returned
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;

    /**
     * Creates a successful response with data
     * 
     * @param message Success message
     * @param data Response data
     * @return RestResponse with success=true
     */
    public static <T> RestResponse<T> success(String message, T data) {
        return RestResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .errors(List.of())
                .build();
    }

    /**
     * Creates a successful response without data
     * 
     * @param message Success message
     * @return RestResponse with success=true and null data
     */
    public static <T> RestResponse<T> success(String message) {
        return success(message, null);
    }

    /**
     * Creates an error response with error details
     * 
     * @param message Error message
     * @param errors List of specific error details
     * @return RestResponse with success=false
     */
    public static <T> RestResponse<T> error(String message, List<String> errors) {
        return RestResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .errors(errors != null ? errors : List.of())
                .build();
    }

    /**
     * Creates an error response with single error message
     * 
     * @param message Error message
     * @param error Single error detail
     * @return RestResponse with success=false
     */
    public static <T> RestResponse<T> error(String message, String error) {
        return error(message, List.of(error));
    }

    /**
     * Creates an error response with only message
     * 
     * @param message Error message
     * @return RestResponse with success=false and empty errors list
     */
    public static <T> RestResponse<T> error(String message) {
        return error(message, List.of());
    }
}
