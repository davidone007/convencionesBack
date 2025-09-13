package co.edu.icesi.planeacionpostgrados.service;

import co.edu.icesi.planeacionpostgrados.dto.LoginInDTO;
import co.edu.icesi.planeacionpostgrados.dto.LoginOutDTO;
import co.edu.icesi.planeacionpostgrados.dto.UserDTO;

import java.util.List;

/**
 * Service interface for User operations.
 * Defines the contract for user-related business logic.
 */
public interface UserService {
    
    /**
     * Authenticates a user and returns login information
     * @param loginInDTO Login credentials
     * @return Login response with user information
     */
    LoginOutDTO login(LoginInDTO loginInDTO);
    
    /**
     * Retrieves a user by their ID
     * @param id User ID
     * @return User DTO
     */
    UserDTO getUserById(Long id);
    
    /**
     * Creates a new user
     * @param userDTO User creation data
     * @return Created user DTO
     */
    UserDTO createUser(UserDTO userDTO);
    
    /**
     * Updates an existing user
     * @param id User ID to update
     * @param userDTO User update data
     * @return Updated user DTO
     */
    UserDTO updateUser(Long id, UserDTO userDTO);
    
    /**
     * Deletes a user by ID
     * @param id User ID to delete
     */
    void deleteUser(Long id);
    
    /**
     * Retrieves all users
     * @return List of user DTOs
     */
    List<UserDTO> getAllUsers();
}
