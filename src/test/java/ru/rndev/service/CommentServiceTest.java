package ru.rndev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rndev.dto.*;
import ru.rndev.entity.Comment;
import ru.rndev.entity.News;
import ru.rndev.entity.Role;
import ru.rndev.entity.User;
import ru.rndev.exception.NotAccessRightsException;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.mapper.CommentMapper;
import ru.rndev.repository.CommentRepository;
import ru.rndev.util.Constant;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private NewsService newsService;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private Comment commentCreate;
    private CommentDto commentDto;
    private UserDetails userDetails;
    private CommentCreateDto commentCreateDto;
    private CommentUpdateDto commentUpdateDto;
    private User user;
    private News news;
    private Pageable pageable;
    private Page<Comment> comments;

    @BeforeEach
    void init(){
        comment = getComment();
        commentDto = getCommentDto();
        userDetails = getUserDetails();
        commentCreateDto = getCommentCreateDto();
        commentCreate = getCommentCreate();
        commentUpdateDto = getCommentUpdateDto();
        pageable = PageRequest.of(Constant.PAGE, Constant.SIZE);
        comments = new PageImpl<>(Arrays.asList(comment));
        user = getUser();
        news = getNews();
    }

    @Test
    void findAllTest() {
        Mockito.when(commentRepository.findAll(pageable)).thenReturn(comments);
        Mockito.when(commentMapper.mapToDto(comment)).thenReturn(commentDto);

        assertEquals(1, commentService.findAll(pageable).size());
    }

    @Test
    void findByIdTest() {
        Mockito.when(commentRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(comment));
        Mockito.when(commentMapper.mapToDto(comment)).thenReturn(commentDto);

        assertEquals(commentDto, commentService.findById(Constant.TEST_ID));
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(commentRepository.findById(Constant.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.findById(Constant.TEST_ID));
    }

    @Test
    void saveTest() {
        Mockito.when(userService.getUser(Constant.TEST_USERNAME)).thenReturn(user);
        Mockito.when(newsService.getNews(Constant.TEST_ID)).thenReturn(news);
        Mockito.when(commentRepository.save(commentCreate)).thenReturn(comment);
        Mockito.when(commentMapper.mapToDto(comment)).thenReturn(commentDto);

        assertEquals(commentDto, commentService.save(commentCreateDto, userDetails));
    }

    @Test
    void updateTest() {
        Mockito.when(commentRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(comment));
        Mockito.when(commentMapper.mapToUpdate(comment, commentUpdateDto)).thenReturn(comment);
        Mockito.when(commentRepository.saveAndFlush(comment)).thenReturn(comment);
        Mockito.when(commentMapper.mapToDto(comment)).thenReturn(commentDto);

        assertEquals(commentDto, commentService.update(Constant.TEST_ID, commentUpdateDto, userDetails));
    }

    @Test
    void updateExceptionTest(){
        Mockito.when(commentRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(comment));

        assertThrows(NotAccessRightsException.class, () -> commentService.update(Constant.TEST_ID, commentUpdateDto, getUserDetailsExc()));
    }

    @Test
    void deleteTest() {
        Mockito.when(commentRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(comment));
        assertTrue(commentService.delete(Constant.TEST_ID, userDetails));
        Mockito.verify(commentRepository, Mockito.times(1)).delete(comment);
    }

    private Comment getComment(){
        return Comment.builder()
                .id(Constant.TEST_ID)
                .date(LocalDateTime.now())
                .text(Constant.TEST_TEXT)
                .user(getUser())
                .news(getNews())
                .build();
    }

    private Comment getCommentCreate(){
        return Comment.builder()
                .text(Constant.TEST_TEXT)
                .user(getUser())
                .news(getNews())
                .build();
    }

    private CommentDto getCommentDto(){
        return CommentDto.builder()
                .id(Constant.TEST_ID)
                .date(LocalDateTime.now())
                .text(Constant.TEST_TEXT)
                .user(getUserDto())
                .build();
    }

    private CommentCreateDto getCommentCreateDto(){
        return CommentCreateDto.builder()
                .text(Constant.TEST_TEXT)
                .news_id(Constant.TEST_ID)
                .build();
    }

    private CommentUpdateDto getCommentUpdateDto(){
        return CommentUpdateDto.builder()
                .text(Constant.TEST_TEXT)
                .build();
    }

    private User getUser(){
        return User.builder()
                .id(Constant.TEST_ID)
                .username(Constant.TEST_USERNAME)
                .password(Constant.TEST_PASSWORD)
                .firstname(Constant.TEST_FIRSTNAME)
                .lastname(Constant.TEST_LASTNAME)
                .role(Role.ADMIN)
                .build();
    }

    private UserDto getUserDto(){
        return UserDto.builder()
                .id(Constant.TEST_ID)
                .username(Constant.TEST_USERNAME)
                .firstname(Constant.TEST_FIRSTNAME)
                .lastname(Constant.TEST_LASTNAME)
                .role(Role.ADMIN)
                .build();
    }

    private News getNews() {
        return News.builder()
                .id(Constant.TEST_ID)
                .date(LocalDateTime.now())
                .title(Constant.TEST_TITLE)
                .text(Constant.TEST_TEXT)
                .user(getUser())
                .build();
    }

    private UserDetails getUserDetails(){
        return new org.springframework.security.core.userdetails.User(
                Constant.TEST_USERNAME,
                Constant.TEST_PASSWORD,
                Collections.singleton(Role.ADMIN));
    }

    private UserDetails getUserDetailsExc(){
        return new org.springframework.security.core.userdetails.User(
                Constant.TEST_USERNAME_EMPTY,
                Constant.TEST_PASSWORD,
                Collections.singleton(Role.JOUR));
    }

}