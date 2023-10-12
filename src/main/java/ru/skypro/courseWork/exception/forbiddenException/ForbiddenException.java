package ru.skypro.courseWork.exception.forbiddenException;

/**
 * Класс {@code ForbiddenException} представляет исключение, которое выбрасывается при попытке доступа к ресурсу,
 * к которому у пользователя нет прав доступа.
 *
 * @see RuntimeException
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
