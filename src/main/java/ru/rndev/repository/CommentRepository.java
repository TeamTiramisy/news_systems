package ru.rndev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rndev.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
