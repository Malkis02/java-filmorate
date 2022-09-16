package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class IsBeforeValidator implements ConstraintValidator<IsBefore, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context)throws ValidationException{
        return !date.isBefore(LocalDate.of(1895, 12, 28));
    }
}
