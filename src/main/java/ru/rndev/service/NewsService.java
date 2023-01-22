package ru.rndev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rndev.dto.NewsCreateDto;
import ru.rndev.dto.NewsDto;
import ru.rndev.dto.NewsFilter;
import ru.rndev.entity.News;
import ru.rndev.entity.Role;
import ru.rndev.exception.NotAccessRightsException;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.mapper.NewsMapper;
import ru.rndev.repository.NewsRepository;
import ru.rndev.util.Constant;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private final UserService userService;

    public List<NewsDto> findAll(NewsFilter newsFilter, Pageable pageable) {
        News news = newsMapper.mapFromFilter(newsFilter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher(Constant.FIELD_NAME_TITLE, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher(Constant.FIELD_NAME_TEXT, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        return newsRepository.findAll(Example.of(news, matcher), pageable).stream()
                .map(newsMapper::mapToDto)
                .collect(toList());
    }

    public NewsDto findById(Integer id) {
        return newsRepository.findById(id)
                .map(newsMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'JOUR')")
    @Transactional
    public NewsDto save(NewsCreateDto dto, UserDetails userDetails) {
        return Optional.of(dto)
                .map(newsMapper::mapToEntity)
                .map(news -> {
                    news.setUser(userService.getUser(userDetails.getUsername()));
                    return newsRepository.save(news);
                })
                .map(newsMapper::mapToDto)
                .orElseThrow();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'JOUR')")
    @Transactional
    public NewsDto update(Integer id, NewsCreateDto newsCreateDto, UserDetails userDetails) {
        return newsRepository.findById(id)
                .map(news -> getAuthorize(news, userDetails))
                .map(news -> newsMapper.mapToUpdate(news, newsCreateDto))
                .map(newsRepository::saveAndFlush)
                .map(newsMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'JOUR')")
    @Transactional
    public boolean delete(Integer id, UserDetails userDetails) {
        return newsRepository.findById(id)
                .map(news -> getAuthorize(news, userDetails))
                .map(comment -> {
                    newsRepository.delete(comment);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public News getNews(Integer id){
        return newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    private News getAuthorize(News news, UserDetails userDetails) {
        if (!(userDetails.getUsername().equals(news.getUser().getUsername()) &&
                userDetails.getAuthorities().contains(Role.JOUR))) {
            if (!userDetails.getAuthorities().contains(Role.ADMIN)) {
                throw new NotAccessRightsException(Constant.AUTH_ERROR_CODE);
            }
        }
        return news;
    }

}
