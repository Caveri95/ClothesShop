package ru.skypro.courseWork.apiExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.courseWork.exception.forbiddenException.ForbiddenException;
import ru.skypro.courseWork.exception.invalidParameters.InvalidRegistrationParameters;
import ru.skypro.courseWork.exception.notFoundException.NotFoundException;
/**
 * Вспомогательный класс для обработки исключений, возникающих при работе контроллеров.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerEntityNotFoundException(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidRegistrationParameters.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String invalidParameters(InvalidRegistrationParameters e) {
        return e.getMessage();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handlerForbiddenException(ForbiddenException e) {
        return e.getMessage();
    }
}
