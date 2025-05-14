package org.example.backend.mapper;

import org.example.backend.configuration.MapstructConfig;
import org.example.backend.dto.UserDTO;
import org.example.backend.model.user.User;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {
    UserDTO toUserDto(User user);

}
