package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    private Integer id;
    @NotBlank(message = "электронная почта не может быть пустой и должна содержать символ @")
    @Email
    private String email;
    @NotBlank(message = "логин не может быть пустым и содержать пробелы")
    private String login;
    private String name;
    @PastOrPresent(message = "дата рождения не может быть в будущем.")
    @NotNull
    private LocalDate birthday;
    @JsonIgnore
    private final Set<Integer> friends = new HashSet<>();

}
