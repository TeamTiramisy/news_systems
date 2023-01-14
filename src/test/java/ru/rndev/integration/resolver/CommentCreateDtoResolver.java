package ru.rndev.integration.resolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.rndev.dto.CommentCreateDto;
import ru.rndev.dto.NewsCreateDto;
import ru.rndev.util.Constant;

public class CommentCreateDtoResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == CommentCreateDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return CommentCreateDto.builder()
                .text(Constant.TEST_TEXT_CREATE)
                .username(Constant.TEST_USERNAME_PRESENT)
                .news_id(Constant.TEST_ID)
                .build();
    }
}
