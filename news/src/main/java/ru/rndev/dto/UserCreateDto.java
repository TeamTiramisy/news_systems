package ru.rndev.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import ru.rndev.entity.Role;
import ru.rndev.validation.CreateAction;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDto {

    @Email
    @NotBlank
    String username;

    @Size(min = 3, max = 12)
    @NotBlank(groups = CreateAction.class)
    String password;

    @NotBlank
    String firstname;

    @NotBlank
    String lastname;

    Role role;

}
