package ru.rndev.integration.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.rndev.dto.NewsCreateDto;
import ru.rndev.exception.NotAccessRightsException;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.integration.TestBase;
import ru.rndev.integration.resolver.NewsCreateDtoResolver;
import ru.rndev.util.Constant;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(NewsCreateDtoResolver.class)
class NewsControllerTest extends TestBase {

    private final MockMvc mockMvc;

    private final Gson gson;


    @Test
    @SneakyThrows
    void findAllTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_NEWS))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @SneakyThrows
    void findByIdTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_NEWS_ID, Constant.TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TITLE, Matchers.is(Constant.TEST_TITLE)))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TEXT, Matchers.is(Constant.TEST_TEXT)));
    }

    @Test
    @SneakyThrows
    void findByIdExceptionTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_NEWS_ID, Constant.TEST_FAILED_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals(result.getResolvedException().getMessage(), Constant.MESSAGE));
    }

    @Test
    @SneakyThrows
    void saveTest(NewsCreateDto newsCreateDto) {
        mockMvc.perform(MockMvcRequestBuilders.post(Constant.URL_NEWS)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newsCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TITLE, Matchers.is(Constant.TEST_TITLE_CREATE)))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TEXT, Matchers.is(Constant.TEST_TEXT_CREATE)));
    }

    @Test
    @SneakyThrows
    void updateTest(NewsCreateDto newsCreateDto) {
        mockMvc.perform(MockMvcRequestBuilders.put(Constant.URL_NEWS_ID, Constant.TEST_ID)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newsCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TITLE, Matchers.is(Constant.TEST_TITLE_CREATE)))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TEXT, Matchers.is(Constant.TEST_TEXT_CREATE)));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "petr@mail.ru", password = "123", authorities = {"JOUR"})
    void updateExceptionTest(NewsCreateDto newsCreateDto){
        mockMvc.perform(MockMvcRequestBuilders.put(Constant.URL_NEWS_ID, Constant.TEST_ID)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newsCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NotAccessRightsException))
                .andExpect(result -> Assertions.assertEquals(result.getResolvedException().getMessage(), Constant.AUTHOR_EXCEPTION));
    }

    @Test
    @SneakyThrows
    void deleteTest(){
        mockMvc.perform(MockMvcRequestBuilders.delete(Constant.URL_NEWS_ID, Constant.TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}