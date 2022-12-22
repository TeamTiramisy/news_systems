package ru.rndev.dto;

import lombok.Builder;
import lombok.Value;
import ru.rndev.entity.Role;

@Value
@Builder
public class UserDto {

    Integer id;

    String username;

    String firstname;

    String lastname;

    Role role;

}
