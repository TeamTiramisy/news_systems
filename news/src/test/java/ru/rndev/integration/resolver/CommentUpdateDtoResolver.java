package ru.rndev.integration.resolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.rndev.dto.CommentUpdateDto;
import ru.rndev.util.Constant;

public class CommentUpdateDtoResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == CommentUpdateDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return CommentUpdateDto.builder()
                .text(Constant.TEST_TITLE_CREATE)
                .build();
    }
}
