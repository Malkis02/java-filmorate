package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.IsBefore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@Builder
public class Film {
    private Integer id;
    @NotBlank(message = "Необходимо указать название.")
    private String name;
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    @IsBefore(message = "дата релиза — не раньше 12.28.1895")
    private LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной.")
    private Integer duration;
    private Integer rate;
    private final Set<Integer> likes = new HashSet<>();

    private MPA mpa;
    private final Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId));


    public void addLike(Integer userId) {
        likes.add(userId);
        rate = likes.size();
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

}
