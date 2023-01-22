package ru.rndev.integration.resolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.rndev.dto.NewsCreateDto;
import ru.rndev.util.Constant;

public class NewsCreateDtoResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == NewsCreateDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return NewsCreateDto.builder()
                .title(Constant.TEST_TITLE_CREATE)
                .text(Constant.TEST_TEXT_CREATE)
                .build();
    }
}
