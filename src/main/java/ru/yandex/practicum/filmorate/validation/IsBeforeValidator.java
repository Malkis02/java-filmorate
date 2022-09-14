package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class IsBeforeValidator implements ConstraintValidator<IsBefore, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context)throws ValidationException{
        if(date.isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }else {
            return true;
        }
    }
}
