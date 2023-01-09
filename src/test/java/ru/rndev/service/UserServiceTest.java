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
import ru.rndev.dto.UserCreateDto;
import ru.rndev.dto.UserDto;
import ru.rndev.entity.Role;
import ru.rndev.entity.User;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.mapper.UserMapper;
import ru.rndev.repository.UserRepository;
import ru.rndev.util.Constant;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;
    private UserCreateDto userCreateDto;
    private Pageable pageable;
    private Page<User> users;

    @BeforeEach
    void init(){
        user = getUser();
        userDto = getUserDto();
        userCreateDto = getUserCreateDto();
        pageable = PageRequest.of(Constant.PAGE, Constant.SIZE);
        users = new PageImpl<>(Arrays.asList(user));
    }

    @Test
    void findAllTest() {
        Mockito.when(userRepository.findAll(pageable)).thenReturn(users);
        Mockito.when(userMapper.mapToDto(user)).thenReturn(userDto);

        assertEquals(1, userService.findAll(pageable).size());
    }

    @Test
    void findByIdTest() {
        Mockito.when(userRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(user));
        Mockito.when(userMapper.mapToDto(user)).thenReturn(userDto);

        assertEquals(userDto, userService.findById(Constant.TEST_ID));
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(userRepository.findById(Constant.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(Constant.TEST_ID));
    }

    @Test
    void findByUserNameTest() {
        Mockito.when(userRepository.findByUsername(Constant.TEST_USERNAME)).thenReturn(Optional.ofNullable(user));
        Mockito.when(userMapper.mapToDto(user)).thenReturn(userDto);

        assertEquals(userDto, userService.findByUserName(Constant.TEST_USERNAME));
    }

    @Test
    void findByUsernameExceptionTest() {
        Mockito.when(userRepository.findByUsername(Constant.TEST_USERNAME)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findByUserName(Constant.TEST_USERNAME));
    }

    @Test
    void saveTest() {
        Mockito.when(userMapper.mapToEntity(userCreateDto)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.mapToDto(user)).thenReturn(userDto);

        assertEquals(userDto, userService.save(userCreateDto));
    }

    @Test
    void updateTest() {
        Mockito.when(userRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(user));
        Mockito.when(userMapper.mapToUpdate(user, userCreateDto)).thenReturn(user);
        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
        Mockito.when(userMapper.mapToDto(user)).thenReturn(userDto);

        assertEquals(userDto, userService.update(Constant.TEST_ID, userCreateDto));
    }

    @Test
    void deleteTest() {
        Mockito.when(userRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(user));
        assertTrue(userService.delete(Constant.TEST_ID));
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    private User getUser(){
        return User.builder()
                .id(Constant.TEST_ID)
                .username(Constant.TEST_USERNAME)
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

    private UserCreateDto getUserCreateDto(){
        return UserCreateDto.builder()
                .username(Constant.TEST_USERNAME)
                .firstname(Constant.TEST_FIRSTNAME)
                .lastname(Constant.TEST_LASTNAME)
                .role(Role.ADMIN)
                .build();
    }

}