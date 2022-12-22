package ru.rndev.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CommentDto {

    Integer id;

    LocalDateTime date;

    String text;

    UserDto user;

}
