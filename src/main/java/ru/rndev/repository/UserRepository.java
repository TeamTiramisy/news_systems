package ru.rndev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rndev.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
