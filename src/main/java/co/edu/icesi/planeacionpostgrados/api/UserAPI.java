package co.edu.icesi.planeacionpostgrados.api;

import co.edu.icesi.planeacionpostgrados.dto.LoginInDTO;
import co.edu.icesi.planeacionpostgrados.dto.LoginOutDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserAPI {
    @PostMapping("/authenticate")
    @PreAuthorize("permitAll()")
    ResponseEntity<LoginOutDTO> login(@RequestBody LoginInDTO loginInDTO);
}