package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @SpyBean
    private final UserDbStorage userDbStorage;

    @Test
    void userCreate() throws Exception {
        User user = User.builder()
                .email("practic1337@yandex.ru")
                .login("login1")
                .name("Name")
                .birthday(LocalDate.of(2000,8,20))
                .build();
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    void userCreateWithWrongEmail() throws Exception {
        User user = User.builder()
                .email("practiyandex.ru")
                .login("login2")
                .name("Name")
                .birthday(LocalDate.of(2000,8,20))
                .build();
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
    @Test
    void userCreateWithWrongLogin() throws Exception{
        User user = User.builder()
                .email("pract@yandex.ru")
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
                .id(2)
                .email("practicum777@yandex.ru")
                .login("login3")
                .name(" ")
                .birthday(LocalDate.of(2000,8,20))
                .build();
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(userDbStorage.findUserById(user.getId()).getName(),user.getLogin()));
    }
    @Test
    void userCreateWithWrongBirthday() throws Exception{
        User user = User.builder()
                .email("practicum@yandex.ru")
                .login("login4")
                .name("name")
                .birthday(LocalDate.of(2023,8,20))
                .build();
        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
    @Test
    void userUpdate()throws Exception{
        User user = User.builder()
                .email("prac@yandex.ru")
                .login("login5")
                .name("name")
                .birthday(LocalDate.of(2003,8,20))
                .build();

        User updatedUser = User.builder()
                .id(user.getId())
                .email("updateemail@yandex.ru")
                .login("updatelogin")
                .name("updatename")
                .birthday(LocalDate.of(1997,1,30))
                .build();
        String body = objectMapper.writeValueAsString(updatedUser);
        mockMvc.perform(
                        put("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(userDbStorage.getAllUsers().contains(updatedUser)));
    }
    @Test
    void updateUserWithWrongId()throws Exception{
        User user = User.builder()
                .email("practicum02@yandex.ru")
                .login("login6")
                .name("name")
                .birthday(LocalDate.of(2023,8,20))
                .build();

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