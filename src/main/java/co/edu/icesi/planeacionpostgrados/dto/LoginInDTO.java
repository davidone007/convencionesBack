package co.edu.icesi.planeacionpostgrados.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginInDTO(@Schema(required = true) @NotBlank String username,
                         @Schema(required = true) @NotBlank String password) {
}