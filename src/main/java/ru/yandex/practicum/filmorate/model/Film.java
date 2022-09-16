package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.IsBefore;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank(message = "Необходимо указать название.")
    private String name;
    @Size(max = 200,message = "максимальная длина описания — 200 символов")
    private String description;
    @IsBefore(message = "дата релиза — не раньше 12.28.1895")
    private LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной.")
    private int duration;
}
