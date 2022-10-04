package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.IsBefore;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<Integer> likes = new HashSet<>();
    private Integer rate;

    public void addLike(Integer userId){
        likes.add(userId);
        rate = likes.size();
    }

    public void removeLike(Integer userId){
        likes.remove(userId);
        rate = likes.size();
    }
}
