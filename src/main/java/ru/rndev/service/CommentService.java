package ru.rndev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rndev.dto.CommentCreateDto;
import ru.rndev.dto.CommentDto;
import ru.rndev.dto.CommentUpdateDto;
import ru.rndev.entity.Comment;
import ru.rndev.entity.News;
import ru.rndev.entity.User;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.mapper.CommentMapper;
import ru.rndev.repository.CommentRepository;
import ru.rndev.repository.NewsRepository;
import ru.rndev.repository.UserRepository;
import ru.rndev.util.Constant;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper commentMapper;

    public List<CommentDto> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable).stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public CommentDto findById(Integer id){
        return commentRepository.findById(id)
                .map(commentMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public CommentDto save(CommentCreateDto commentCreateDto){
        User user = userRepository.findByUsername(commentCreateDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_USERNAME,
                        commentCreateDto.getUsername(), Constant.ERROR_CODE));

        News news = newsRepository.findById(commentCreateDto.getNews_id())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID,
                        commentCreateDto.getNews_id(), Constant.ERROR_CODE));

        return Optional.of(create(user, news, commentCreateDto.getText()))
                .map(commentRepository::save)
                .map(commentMapper::mapToDto)
                .orElseThrow();

    }

    @Transactional
    public CommentDto update(Integer id, CommentUpdateDto commentUpdateDto){
        return commentRepository.findById(id)
                .map(comment -> commentMapper.mapToUpdate(comment, commentUpdateDto))
                .map(commentRepository::saveAndFlush)
                .map(commentMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public boolean delete(Integer id){
        return commentRepository.findById(id)
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

}
