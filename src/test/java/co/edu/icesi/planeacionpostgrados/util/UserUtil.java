package co.edu.icesi.planeacionpostgrados.util;

import co.edu.icesi.planeacionpostgrados.model.User;

public class UserUtil {
    public static User   user() {
        return User.builder()
                .id(1L)
                .name("John Doe")
                .documentId("123456789")
                .build();
    }
}
