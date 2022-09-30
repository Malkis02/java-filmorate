package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class IdValidationException extends RuntimeException {

    public IdValidationException(String s){
        super(s);
    }
}
