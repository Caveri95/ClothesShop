package ru.skypro.courseWork.exception.invalidParameters;

public class InvalidUsernameException extends InvalidRegistrationParameters {

    public InvalidUsernameException() {
        super("Пользователь с таким email уже зарегистрирован");
    }
}
