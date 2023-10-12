package ru.skypro.courseWork.exception.invalidParameters;

/**
 * Класс {@code InvalidRegistrationParameters} представляет исключение, которое выбрасывается при попытке
 * обработки данных не соответствующих ожидаемому формату при регистрации пользователя.
 *
 * @see RuntimeException
 */
public class InvalidRegistrationParameters extends RuntimeException {

    public InvalidRegistrationParameters(String message) {
        super(message);
    }
}
