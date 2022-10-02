package ru.yandex.practicum.filmorate.exception;

public class MethodArgumentNotValidException extends RuntimeException{

    public MethodArgumentNotValidException(String s){
        super(s);
    }
}
