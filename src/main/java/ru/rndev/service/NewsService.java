package ru.rndev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rndev.dto.NewsCreateDto;
import ru.rndev.dto.NewsDto;
import ru.rndev.dto.NewsFilter;
import ru.rndev.entity.News;
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

    public List<NewsDto> findAll(NewsFilter newsFilter, Pageable pageable){
        News news = newsMapper.mapFromFilter(newsFilter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher(Constant.FIELD_NAME_TITLE, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher(Constant.FIELD_NAME_TEXT, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        return newsRepository.findAll(Example.of(news, matcher), pageable).stream()
                .map(newsMapper::mapToDto)
                .collect(toList());
    }

    public NewsDto findById(Integer id){
        return newsRepository.findById(id)
                .map(newsMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public NewsDto save(NewsCreateDto dto){
        return Optional.of(dto)
                .map(newsMapper::mapToEntity)
                .map(newsRepository::save)
                .map(newsMapper::mapToDto)
                .orElseThrow();
    }

    @Transactional
    public NewsDto update(Integer id, NewsCreateDto newsCreateDto){
        return newsRepository.findById(id)
                .map(news -> newsMapper.mapToUpdate(news, newsCreateDto))
                .map(newsRepository::saveAndFlush)
                .map(newsMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public boolean delete(Integer id){
        return newsRepository.findById(id)
                .map(comment -> {
                    newsRepository.delete(comment);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }
}
