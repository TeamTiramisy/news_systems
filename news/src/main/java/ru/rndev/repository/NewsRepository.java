package ru.rndev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rndev.entity.News;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
