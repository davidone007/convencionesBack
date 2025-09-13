package co.edu.icesi.planeacionpostgrados.mapper;

import co.edu.icesi.planeacionpostgrados.dto.UserDTO;
import co.edu.icesi.planeacionpostgrados.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for converting between User entity and UserDTO.
 * Automatically generates conversion methods at compile time.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Converts UserDTO to User entity for creation
     * @param userDTO The user DTO
     * @return User entity (without ID)
     */
    @Mapping(source = "id", target = "id")
    User toEntity(UserDTO userDTO);

    /**
     * Converts User entity to UserDTO
     * @param user The user entity
     * @return UserDTO
     */
    @Mapping(source = "id", target = "id")
    UserDTO toDTO(User user);

    /**
     * Updates an existing User entity with data from UserDTO
     * @param userDTO The user DTO with updated data
     * @param user The existing user entity to update
     */
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UserDTO userDTO, @MappingTarget User user);
}
