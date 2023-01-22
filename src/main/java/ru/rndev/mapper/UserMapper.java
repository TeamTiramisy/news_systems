package ru.rndev.mapper;

import org.mapstruct.*;
import ru.rndev.dto.UserCreateDto;
import ru.rndev.dto.UserDto;
import ru.rndev.entity.User;
import ru.rndev.mapper.annotation.EncodedMapping;

@Mapper(uses = PasswordEncoderMapper.class)
public interface UserMapper {

    UserDto mapToDto(User user);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User mapToEntity(UserCreateDto userCreateDto);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User mapToUpdate(@MappingTarget User user, UserCreateDto userCreateDto);

}
