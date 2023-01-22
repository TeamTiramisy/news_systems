package ru.rndev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rndev.dto.UserCreateDto;
import ru.rndev.dto.UserDto;
import ru.rndev.entity.User;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.mapper.UserMapper;
import ru.rndev.repository.UserRepository;
import ru.rndev.util.Constant;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll(Pageable pageable){
        return userRepository.findAll(pageable).stream()
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    public UserDto findById(Integer id){
        return userRepository.findById(id)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    public UserDto findByUserName(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_USERNAME, username, Constant.ERROR_CODE));
    }

    @Transactional
    public UserDto save(UserCreateDto userCreateDto){
        return Optional.of(userCreateDto)
                .map(userMapper::mapToEntity)
                .map(userRepository::save)
                .map(userMapper::mapToDto)
                .orElseThrow();
    }

    @Transactional
    public UserDto update(Integer id, UserCreateDto userCreateDto){
        return userRepository.findById(id)
                .map(user -> userMapper.mapToUpdate(user, userCreateDto))
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public boolean delete(Integer id){
        return userRepository.findById(id)
                .map(comment -> {
                    userRepository.delete(comment);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public User getUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_USERNAME, username, Constant.ERROR_CODE));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException(Constant.AUTH_EXCEPTION + username));
    }

}
