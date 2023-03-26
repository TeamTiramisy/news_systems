package ru.rndev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rndev.dto.CommentCreateDto;
import ru.rndev.dto.CommentDto;
import ru.rndev.dto.CommentUpdateDto;
import ru.rndev.entity.Comment;
import ru.rndev.entity.News;
import ru.rndev.entity.Role;
import ru.rndev.entity.User;
import ru.rndev.exception.NotAccessRightsException;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.mapper.CommentMapper;
import ru.rndev.repository.CommentRepository;
import ru.rndev.util.Constant;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "comment")
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;
    private final CommentMapper commentMapper;

    public List<CommentDto> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable).stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    @Cacheable
    public CommentDto findById(Integer id){
        return commentRepository.findById(id)
                .map(commentMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public CommentDto save(CommentCreateDto commentCreateDto, UserDetails userDetails){
        return Optional.of(create(userService.getUser(userDetails.getUsername()),
                        newsService.getNews(commentCreateDto.getNews_id()),
                        commentCreateDto.getText()))
                .map(commentRepository::save)
                .map(commentMapper::mapToDto)
                .orElseThrow();

    }

    @CachePut(key = "#id")
    @Transactional
    public CommentDto update(Integer id, CommentUpdateDto commentUpdateDto, UserDetails userDetails){
        return commentRepository.findById(id)
                .map(comment -> getAuthorize(comment, userDetails))
                .map(comment -> commentMapper.mapToUpdate(comment, commentUpdateDto))
                .map(commentRepository::saveAndFlush)
                .map(commentMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @CacheEvict(key = "#id")
    @Transactional
    public boolean delete(Integer id, UserDetails userDetails){
        return commentRepository.findById(id)
                .map(comment -> getAuthorize(comment, userDetails))
                .map(comment -> {
                    commentRepository.delete(comment);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    private Comment create(User user, News news, String text){
        return Comment.builder()
                .text(text)
                .user(user)
                .news(news)
                .build();
    }

    private Comment getAuthorize(Comment comment, UserDetails userDetails) {
        if (!userDetails.getUsername().equals(comment.getUser().getUsername())) {
            if (!userDetails.getAuthorities().contains(Role.ADMIN)) {
                throw new NotAccessRightsException(Constant.AUTH_ERROR_CODE);
            }
        }

        return comment;
    }

}
