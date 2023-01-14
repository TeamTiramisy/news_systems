package ru.rndev.integration.resolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.rndev.dto.UserCreateDto;
import ru.rndev.entity.Role;
import ru.rndev.util.Constant;

public class UserCreateDtoResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserCreateDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return UserCreateDto.builder()
                .username(Constant.TEST_USERNAME)
                .firstname(Constant.TEST_FIRSTNAME)
                .lastname(Constant.TEST_LASTNAME)
                .role(Role.USER)
                .build();
    }
}
