package ru.rndev.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class NewsDto {

    Integer id;

    LocalDateTime date;

    String title;

    String text;

    UserDto user;

    List<CommentDto> comments;

}
