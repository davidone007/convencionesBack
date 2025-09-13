
package co.edu.icesi.planeacionpostgrados.unit.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.icesi.planeacionpostgrados.dto.UserDTO;
import co.edu.icesi.planeacionpostgrados.exception.UserNotFoundException;
import co.edu.icesi.planeacionpostgrados.mapper.UserMapper;
import co.edu.icesi.planeacionpostgrados.model.User;
import co.edu.icesi.planeacionpostgrados.repository.UserRepository;
import co.edu.icesi.planeacionpostgrados.service.UserServiceImpl;
import co.edu.icesi.planeacionpostgrados.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldReturnUserWhenUserExists() {
        // Arrange
        Long userId = 1L;
        User user = UserUtil.user();
        UserDTO expectedResponse = UserDTO.builder()
                .id(userId)
                .name(user.getName())
                .documentId(user.getDocumentId())
                .build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(expectedResponse);

        // Act
        UserDTO result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals(user.getName(), result.name());
        assertEquals(user.getDocumentId(), result.documentId());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class, 
            () -> userService.getUserById(userId)
        );
        
        assertEquals("User with ID " + userId + " not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).toDTO(any());
    }

    @Test
    public void testCreateUser() {
        //TODO: Implement when UserService has createUser method
    }

    @Test
    public void testUpdateUser() {
        //TODO: Implement when UserService has updateUser method
    }

    @Test
    public void testDeleteUser() {
        //TODO: Implement when UserService has deleteUser method
    }
}