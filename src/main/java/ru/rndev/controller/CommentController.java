package ru.rndev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rndev.dto.CommentCreateDto;
import ru.rndev.dto.CommentDto;
import ru.rndev.dto.CommentUpdateDto;
import ru.rndev.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(commentService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> findById(@PathVariable Integer id){
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDto> save(@RequestBody CommentCreateDto commentCreateDto){
        return new ResponseEntity<>(commentService.save(commentCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Integer id,
                                             @RequestBody CommentUpdateDto commentUpdateDto){
        return new ResponseEntity<>(commentService.update(id, commentUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
