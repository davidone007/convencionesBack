package co.edu.icesi.planeacionpostgrados.exception;

import co.edu.icesi.planeacionpostgrados.enums.PlaneacionpostgradosExceptionType;
import jakarta.validation.constraints.NotNull;

public class PlaneacionpostgradosException extends RuntimeException {

    private final PlaneacionpostgradosExceptionType pfExceptionType;

    private static final long serialVersionUID = 1L;

    public PlaneacionpostgradosException(@NotNull PlaneacionpostgradosExceptionType pfExceptionType) {
        super(pfExceptionType.getMessage());
        this.pfExceptionType = pfExceptionType;
    }
    
}
