package ru.rndev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rndev.dto.NewsCreateDto;
import ru.rndev.dto.NewsDto;
import ru.rndev.dto.NewsFilter;
import ru.rndev.dto.UserDto;
import ru.rndev.entity.News;
import ru.rndev.entity.Role;
import ru.rndev.entity.User;
import ru.rndev.exception.NotAccessRightsException;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.mapper.NewsMapper;
import ru.rndev.repository.NewsRepository;
import ru.rndev.util.Constant;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private NewsService newsService;

    private News news;
    private User user;
    private NewsDto newsDto;
    private NewsFilter newsFilter;
    private UserDetails userDetails;
    private NewsCreateDto newsCreateDto;
    private Pageable pageable;
    private ExampleMatcher matcher;
    private Page<News> page;

    @BeforeEach
    void init() {
        news = getNews();
        user = getUser();
        newsDto = getNewsDto();
        newsFilter = getNewsFilter();
        userDetails = getUserDetails();
        newsCreateDto = getNewsCreateDto();
        pageable = PageRequest.of(Constant.PAGE, Constant.SIZE);
        matcher = getMatcher();
        page = new PageImpl<>(Arrays.asList(news));
    }

    @Test
    void findAllTest(){
        Mockito.when(newsMapper.mapFromFilter(newsFilter)).thenReturn(news);
        Mockito.when(newsRepository.findAll(Example.of(news, matcher), pageable)).thenReturn(page);

        Mockito.when(newsMapper.mapToDto(news)).thenReturn(newsDto);

        assertEquals(1, newsService.findAll(newsFilter, pageable).size());
    }

    @Test
    void findByIdTest(){
        Mockito.when(newsRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(news));
        Mockito.when(newsMapper.mapToDto(news)).thenReturn(newsDto);

        assertEquals(newsDto, newsService.findById(Constant.TEST_ID));
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(newsRepository.findById(Constant.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> newsService.findById(Constant.TEST_ID));
    }

    @Test
    void saveTest(){
        Mockito.when(newsMapper.mapToEntity(newsCreateDto)).thenReturn(news);
        Mockito.when(userService.getUser(Constant.TEST_USERNAME)).thenReturn(user);
        Mockito.when(newsRepository.save(news)).thenReturn(news);
        Mockito.when(newsMapper.mapToDto(news)).thenReturn(newsDto);

        assertEquals(newsDto, newsService.save(newsCreateDto, userDetails));
    }

    @Test
    void updateTest(){
        Mockito.when(newsRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(news));
        Mockito.when(newsMapper.mapToUpdate(news, newsCreateDto)).thenReturn(news);
        Mockito.when(newsRepository.saveAndFlush(news)).thenReturn(news);
        Mockito.when(newsMapper.mapToDto(news)).thenReturn(newsDto);

        assertEquals(newsDto, newsService.update(Constant.TEST_ID, newsCreateDto, userDetails));
    }

    @Test
    void updateExceptionTest(){
        Mockito.when(newsRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(news));

        assertThrows(NotAccessRightsException.class, () -> newsService.update(Constant.TEST_ID, newsCreateDto, getUserDetailsExc()));
    }

    @Test
    void deleteTest() {
        Mockito.when(newsRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(news));
        assertTrue(newsService.delete(Constant.TEST_ID, userDetails));
        Mockito.verify(newsRepository, Mockito.times(1)).delete(news);
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

    private NewsDto getNewsDto() {
        return NewsDto.builder()
                .id(Constant.TEST_ID)
                .date(LocalDateTime.now())
                .title(Constant.TEST_TITLE)
                .text(Constant.TEST_TEXT)
                .user(getUserDto())
                .build();
    }

    private NewsCreateDto getNewsCreateDto(){
        return NewsCreateDto.builder()
                .title(Constant.TEST_TITLE)
                .text(Constant.TEST_TEXT)
                .build();
    }

    private NewsFilter getNewsFilter() {
        return NewsFilter.builder()
                .title(Constant.TEST_TITLE_CONTAINS)
                .text(Constant.TEST_TEXT_CONTAINS)
                .build();
    }

    private ExampleMatcher getMatcher() {
        return ExampleMatcher.matching()
                .withMatcher(Constant.FIELD_NAME_TITLE, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher(Constant.FIELD_NAME_TEXT, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
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

    private UserDetails getUserDetails(){
        return new org.springframework.security.core.userdetails.User(
                Constant.TEST_USERNAME,
                Constant.TEST_PASSWORD,
                Collections.singleton(Role.ADMIN));
    }

    private UserDetails getUserDetailsExc(){
        return new org.springframework.security.core.userdetails.User(
                Constant.TEST_USERNAME,
                Constant.TEST_PASSWORD,
                Collections.singleton(Role.USER));
    }

}