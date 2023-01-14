package ru.rndev.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.rndev.entity.User;
import ru.rndev.integration.TestBase;
import ru.rndev.repository.UserRepository;
import ru.rndev.util.Constant;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends TestBase {

    private final UserRepository userRepository;

    @Test
    void findByUsernameTest() {
        Optional<User> maybeUser = userRepository.findByUsername(Constant.TEST_USERNAME_PRESENT);

        maybeUser.ifPresent(user -> assertEquals(Constant.TEST_USERNAME_PRESENT, user.getUsername()));

        assertTrue(maybeUser.isPresent());
    }

    @Test
    void findByUsernameEmptyTest() {
        Optional<User> maybeUser = userRepository.findByUsername(Constant.TEST_USERNAME_EMPTY);

        assertFalse(maybeUser.isPresent());
    }
}