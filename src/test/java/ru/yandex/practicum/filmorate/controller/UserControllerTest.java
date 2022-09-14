package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @SpyBean
    UserController userController;

    @Test
    void userCreate() throws Exception {
        User user = User.builder()
                .id(1)
                .email("practicum@yandex.ru")
                .login("login")
                .name("Name")
                .birthday(LocalDate.of(2000,8,20))
                .build();
        userController.create(user);
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    void userCreateWithWrongEmail() throws Exception {
        User user = User.builder()
                .id(1)
                .email("practicumyandex.ru")
                .login("login")
                .name("Name")
                .birthday(LocalDate.of(2000,8,20))
                .build();
        userController.create(user);
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void userCreateWithWrongLogin() throws Exception{
        User user = User.builder()
                .id(1)
                .email("practicum@yandex.ru")
                .login("login login")
                .name("Name")
                .birthday(LocalDate.of(2000,8,20))
                .build();
            String body = objectMapper.writeValueAsString(user);
            mockMvc.perform(
                            post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
    }
    @Test
    void userCreateWithWrongName() throws Exception {
        User user = User.builder()
                .id(1)
                .email("practicum@yandex.ru")
                .login("login")
                .name(" ")
                .birthday(LocalDate.of(2000,8,20))
                .build();
        userController.create(user);
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(user.getName(),user.getLogin()));
    }
    @Test
    void userCreateWithWrongBirthday() throws Exception{
        User user = User.builder()
                .id(1)
                .email("practicum@yandex.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2023,8,20))
                .build();
        userController.create(user);
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void userUpdate()throws Exception{
        User user = User.builder()
                .id(1)
                .email("practicum@yandex.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2003,8,20))
                .build();
        userController.create(user);

        User updatedUser = User.builder()
                .id(1)
                .email("updateemail@yandex.ru")
                .login("updatelogin")
                .name("updatename")
                .birthday(LocalDate.of(1997,1,30))
                .build();
        String body = objectMapper.writeValueAsString(updatedUser);
        mockMvc.perform(
                        put("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(userController.getAllUsers().contains(updatedUser)));
    }
    @Test
    void updateUserWithWrongId()throws Exception{
        User user = User.builder()
                .id(1)
                .email("practicum@yandex.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2023,8,20))
                .build();
        userController.create(user);

        User updatedUser = User.builder()
                .id(999)
                .email("updateemail@yandex.ru")
                .login("updatelogin")
                .name("updatename")
                .birthday(LocalDate.of(1997,1,30))
                .build();
        String body = objectMapper.writeValueAsString(updatedUser);
        mockMvc.perform(
                        put("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}