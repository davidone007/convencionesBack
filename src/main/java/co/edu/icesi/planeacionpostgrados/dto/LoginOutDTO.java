package co.edu.icesi.planeacionpostgrados.dto;

import lombok.Builder;

@Builder
public record LoginOutDTO(
        long userId,
        String userUsername,
        String userExtId,
        String userEmail,
        String userPhone,
        String userName,
        String userLastname,
        String userDocumentId,
        String accessToken,
        String tokenType,
        String systemHomePage
) {
}
