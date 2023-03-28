package ru.rndev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rndev.mapper.annotation.EncodedMapping;

@Component
@RequiredArgsConstructor
public class PasswordEncoderMapper {

    private final PasswordEncoder passwordEncoder;


    @EncodedMapping
    public String encode(String password){
        return passwordEncoder.encode(password);
    }

}
