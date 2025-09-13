package co.edu.icesi.planeacionpostgrados.service;

import co.edu.icesi.planeacionpostgrados.dto.LoginInDTO;
import co.edu.icesi.planeacionpostgrados.dto.LoginOutDTO;
import co.edu.icesi.planeacionpostgrados.dto.UserDTO;
import co.edu.icesi.planeacionpostgrados.exception.UserNotFoundException;
import co.edu.icesi.planeacionpostgrados.mapper.UserMapper;
import co.edu.icesi.planeacionpostgrados.model.User;
import co.edu.icesi.planeacionpostgrados.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserService interface.
 * Handles all user-related business logic and data operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public LoginOutDTO login(LoginInDTO loginInDTO) {
        //TODO: Implement login logic
        log.info("Login attempt for user: {}", loginInDTO);
        return null;
    }

    @Override
    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        return userMapper.toDTO(user);
    }

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user with name: {}", userDTO.name());
        
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        
        log.info("User created successfully with ID: {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        userMapper.updateEntityFromDTO(userDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);
        
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}