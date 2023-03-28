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
import ru.rndev.dto.UserCreateDto;
import ru.rndev.entity.Role;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.integration.TestBase;
import ru.rndev.integration.resolver.UserCreateDtoResolver;
import ru.rndev.util.Constant;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(UserCreateDtoResolver.class)
class UserControllerTest extends TestBase {

    private final MockMvc mockMvc;

    private final Gson gson;


    @Test
    @SneakyThrows
    void findAllTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_USER))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "ruslan@mail.ru", password = "123", authorities = {"USER"})
    void findAllExceptionTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_USER))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    void findByIdTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_USER_ID, Constant.TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_ROLE, Matchers.is(Role.ADMIN.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_USERNAME, Matchers.is(Constant.TEST_USERNAME_PRESENT)));
    }

    @Test
    @SneakyThrows
    void findByIdExceptionTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_USER_ID, Constant.TEST_FAILED_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals(result.getResolvedException().getMessage(), Constant.MESSAGE));
    }

    @Test
    @SneakyThrows
    void findByUsernameTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_USER_USERNAME, Constant.TEST_USERNAME_PRESENT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_ROLE, Matchers.is(Role.ADMIN.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_USERNAME, Matchers.is(Constant.TEST_USERNAME_PRESENT)));
    }

    @Test
    @SneakyThrows
    void findByUsernameExceptionTest(){
        mockMvc.perform(MockMvcRequestBuilders.get(Constant.URL_USER_USERNAME, Constant.TEST_USERNAME_EMPTY))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> Assertions.assertEquals(result.getResolvedException().getMessage(), Constant.MESSAGE_EXCEPTION));
    }

    @Test
    @SneakyThrows
    void saveTest(UserCreateDto userCreateDto) {
        mockMvc.perform(MockMvcRequestBuilders.post(Constant.URL_USER)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(userCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_ROLE, Matchers.is(Role.USER.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_USERNAME, Matchers.is(Constant.TEST_USERNAME)));
    }

    @Test
    @SneakyThrows
    void updateTest(UserCreateDto userCreateDto) {
        mockMvc.perform(MockMvcRequestBuilders.put(Constant.URL_USER_ID, Constant.TEST_ID)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(userCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_ROLE, Matchers.is(Role.USER.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath(Constant.PATH_USERNAME, Matchers.is(Constant.TEST_USERNAME)));
    }

    @Test
    @SneakyThrows
    void deleteTest(){
        mockMvc.perform(MockMvcRequestBuilders.delete(Constant.URL_USER_ID, Constant.TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}