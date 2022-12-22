package ru.rndev.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.rndev.dto.UserCreateDto;
import ru.rndev.dto.UserDto;
import ru.rndev.entity.User;

@Mapper
public interface UserMapper {

    UserDto mapToDto(User user);

    User mapToEntity(UserCreateDto userCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User mapToUpdate(@MappingTarget User user, UserCreateDto userCreateDto);
}
