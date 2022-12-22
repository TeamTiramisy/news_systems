package ru.rndev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.rndev.dto.CommentDto;
import ru.rndev.dto.CommentUpdateDto;
import ru.rndev.entity.Comment;

@Mapper
public interface CommentMapper {

    CommentDto mapToDto(Comment comment);

    Comment mapToUpdate(@MappingTarget Comment comment, CommentUpdateDto commentUpdateDto);

}
