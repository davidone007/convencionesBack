package co.edu.icesi.planeacionpostgrados.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PlaneacionpostgradosExceptionType {

    USER_NOT_FOUND(13, "USER NOT FOUND", ParameterNameConstants.USER_ID, HttpStatus.NOT_FOUND, LogLevel.INFO);

    private final int code;
    private final String message;
    private final String parameterName; 
    private final HttpStatus responseStatus; 
    private final LogLevel logLevel;

    private static class ParameterNameConstants {
        public static final String USER_ID = "userId";
    }
}