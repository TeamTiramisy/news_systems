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
import ru.rndev.dto.CommentCreateDto;
import ru.rndev.dto.CommentUpdateDto;
import ru.rndev.exception.NotAccessRightsException;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.integration.TestBase;
import ru.rndev.integration.resolver.CommentCreateDtoResolver;
import ru.rndev.integration.resolver.CommentUpdateDtoResolver;
import ru.rndev.util.Constant;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(CommentCreateDtoResolver.class)
@ExtendWith(CommentUpdateDtoResolver.class)
class CommentControllerTest extends TestBase {

    private final MockMvc mockMvc;

    private final Gson gson;


    @Test
    @SneakyThrows
    void findAllTest() {
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_COMMENT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @SneakyThrows
    void findByIdTest() {
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_COMMENT_ID, Constant.TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TEXT, Matchers.is(Constant.COMMENT)))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_USER_ID, Matchers.is(Constant.TEST_ID)));
    }

    @Test
    @SneakyThrows
    void findByIdExceptionTest() {
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_COMMENT_ID, Constant.TEST_FAILED_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals(result.getResolvedException().getMessage(), Constant.MESSAGE));
    }

    @Test
    @SneakyThrows
    void saveTest(CommentCreateDto commentCreateDto) {
        mockMvc.perform(MockMvcRequestBuilders.post(Constant.URL_COMMENT)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(commentCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TEXT, Matchers.is(Constant.TEST_TEXT_CREATE)))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_USER_ID, Matchers.is(Constant.TEST_ID)));
    }

    @Test
    @SneakyThrows
    void updateTest(CommentUpdateDto commentUpdateDto) {
        mockMvc.perform(MockMvcRequestBuilders.put(Constant.URL_COMMENT_ID, Constant.TEST_ID)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(commentUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_TEXT, Matchers.is(Constant.TEST_TITLE_CREATE)))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_USER_ID, Matchers.is(Constant.TEST_ID)));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "petr@mail.ru", password = "123", authorities = {"USER"})
    void updateExceptionTest(CommentUpdateDto commentUpdateDto){
        mockMvc.perform(MockMvcRequestBuilders.put(Constant.URL_COMMENT_ID, Constant.TEST_ID)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(commentUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NotAccessRightsException))
                .andExpect(result -> Assertions.assertEquals(result.getResolvedException().getMessage(), Constant.AUTHOR_EXCEPTION));
    }

    @Test
    @SneakyThrows
    void deleteTest(){
        mockMvc.perform(MockMvcRequestBuilders.delete(Constant.URL_COMMENT_ID, Constant.TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}