package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.user.UserRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users found")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        List<UserResponse> users = userService.findAll();
        return ApiResponse.success("Users listed", users);
    }

    @Operation(summary = "Get user by id", description = "Returns a user by id")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getById(@PathVariable Long id) {
        UserResponse user = userService.findById(id);
        return ApiResponse.success("User found", user);
    }

    @Operation(summary = "Create user", description = "Creates a new user")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created")
    @PostMapping
    public ApiResponse<UserResponse> create(@Valid @RequestBody UserRequest request) {
        UserResponse user = userService.create(request);
        return ApiResponse.success("User created", user);
    }

    @Operation(summary = "Update user", description = "Updates an existing user")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated")
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.update(id, request);
        return ApiResponse.success("User updated", user);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by id")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "User deleted")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ApiResponse.success("User deleted", null);
    }
}
