package co.edu.icesi.planeacionpostgrados.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserDTO(
        Long id,
        @NotNull
        @Size(max = 255)
        String name,
        @NotNull
        @Size(max = 25)
        String documentId
) implements Serializable {
}
