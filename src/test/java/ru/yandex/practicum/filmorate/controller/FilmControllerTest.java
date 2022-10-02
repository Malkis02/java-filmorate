package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    FilmController filmController;


    @Test
    void filmCreate() throws Exception {
        Film film = Film.builder()
                .name("Test")
                .description("Test description")
                .releaseDate(LocalDate.of(1999,8,15))
                .duration(100)
                .build();
                filmController.create(film);
        mockMvc.perform(
                        post("/films").content(objectMapper.writeValueAsString(film)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void filmCreateWithWrongName()throws Exception{
        Film film = Film.builder()
                .name(" ")
                .description("Test description")
                .releaseDate(LocalDate.of(1999,8,15))
                .duration(100)
                .build();
        mockMvc.perform(
                        post("/films").content(objectMapper.writeValueAsString(film)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
    @Test
    void filmCreateWithWrongDescription()throws Exception{
        Film film = Film.builder()
                .name("Test")
                .description("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
                        "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. " +
                        "о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.")
                .releaseDate(LocalDate.of(1999,8,15))
                .duration(100)
                .build();
        mockMvc.perform(
                        post("/films").content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().is5xxServerError());
    }
    @Test
    void filmCreateWithWrongReleaseDate()throws Exception{
        Film film = Film.builder()
                .name("Testname")
                .description("Test description")
                .releaseDate(LocalDate.of(1800,8,15))
                .duration(100)
                .build();
        mockMvc.perform(
                        post("/films").content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().is5xxServerError());
    }
    @Test
    void filmCreateWithWrongDuration()throws Exception{
        Film film = Film.builder()
                .name("Testname")
                .description("Test description")
                .releaseDate(LocalDate.of(1999,8,15))
                .duration(-100)
                .build();
        mockMvc.perform(
                        post("/films").content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().is5xxServerError());
    }
    @Test
    void filmUpdate()throws Exception{
        Film film = Film.builder()
                .id(1)
                .name("Test")
                .description("Test description")
                .releaseDate(LocalDate.of(1999,8,15))
                .duration(100)
                .build();
        filmController.create(film);

        Film updatedFilm = Film.builder()
                .id(1)
                .name("Name")
                .description("updated Test description")
                .releaseDate(LocalDate.of(2008,8,15))
                .duration(200)
                .build();

        String body = objectMapper.writeValueAsString(updatedFilm);
        mockMvc.perform(
                        put("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(filmController.getAllFilms().contains(updatedFilm)));
    }
    @Test
    void filmUpdateWithWrongId()throws Exception{
        Film film = Film.builder()
                .id(1)
                .name("Test")
                .description("Test description")
                .releaseDate(LocalDate.of(1999,8,15))
                .duration(100)
                .build();
        filmController.create(film);

        Film updatedFilm = Film.builder()
                .id(-1)
                .name("Name")
                .description("updated Test description")
                .releaseDate(LocalDate.of(2008,8,15))
                .duration(200)
                .build();

        String body = objectMapper.writeValueAsString(updatedFilm);
        mockMvc.perform(
                        put("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}