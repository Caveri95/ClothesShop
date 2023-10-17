package ru.skypro.courseWork.exception.notFoundException;

/**
 * Класс {@code NotFoundException} представляет исключение, которое выбрасывается при попытке
 * найти объект в базе данных, но объект не был обнаружен.*
 *
 * @see RuntimeException
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
