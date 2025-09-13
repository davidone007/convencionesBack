package co.edu.icesi.planeacionpostgrados.controller;

import co.edu.icesi.planeacionpostgrados.dto.LoginInDTO;
import co.edu.icesi.planeacionpostgrados.dto.LoginOutDTO;
import co.edu.icesi.planeacionpostgrados.dto.RestResponse;
import co.edu.icesi.planeacionpostgrados.dto.UserDTO;
import co.edu.icesi.planeacionpostgrados.model.User;
import co.edu.icesi.planeacionpostgrados.dto.UserDTO;
import co.edu.icesi.planeacionpostgrados.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User operations.
 * Follows REST conventions and uses RestResponse envelope pattern.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "API for managing users in the system")
public class UserController {

    private final UserService userService;

    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all users in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers() {
        log.info("GET /api/v1/users - Fetching all users");
        
        List<UserDTO> users = userService.getAllUsers();
        RestResponse<List<UserDTO>> response = RestResponse.success(
            "Users retrieved successfully", 
            users
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User found successfully",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserDTO>> getUserById(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("GET /api/v1/users/{} - Fetching user by ID", id);
        
        UserDTO user = userService.getUserById(id);
        RestResponse<UserDTO> response = RestResponse.success(
            "User retrieved successfully", 
            user
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Create new user",
        description = "Creates a new user with the provided information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "User created successfully",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<RestResponse<UserDTO>> createUser(
            @Parameter(description = "User creation data", required = true)
            @Valid @RequestBody UserDTO userRequest) {
        log.info("POST /api/v1/users - Creating new user: {}", userRequest.name());
        
        UserDTO createdUser = userService.createUser(userRequest);
        RestResponse<UserDTO> response = RestResponse.success(
            "User created successfully", 
            createdUser
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Update user",
        description = "Updates an existing user with the provided information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<UserDTO>> updateUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "User update data", required = true)
            @Valid @RequestBody UserDTO userRequest) {
        log.info("PUT /api/v1/users/{} - Updating user", id);
        
        UserDTO updatedUser = userService.updateUser(id, userRequest);
        RestResponse<UserDTO> response = RestResponse.success(
            "User updated successfully", 
            updatedUser
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Delete user",
        description = "Deletes a user from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User deleted successfully",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Void>> deleteUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/users/{} - Deleting user", id);
        
        userService.deleteUser(id);
        RestResponse<Void> response = RestResponse.success("User deleted successfully");
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "User login",
        description = "Authenticates a user and returns login information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Login successful",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Invalid credentials",
            content = @Content(schema = @Schema(implementation = RestResponse.class))
        )
    })
    @PostMapping("/login")
    public ResponseEntity<RestResponse<LoginOutDTO>> login(
            @Parameter(description = "Login credentials", required = true)
            @Valid @RequestBody LoginInDTO loginInDTO) {
        log.info("POST /api/v1/users/login - Login attempt");
        
        LoginOutDTO loginResult = userService.login(loginInDTO);
        RestResponse<LoginOutDTO> response = RestResponse.success(
            "Login successful", 
            loginResult
        );
        
        return ResponseEntity.ok(response);
    }
}
