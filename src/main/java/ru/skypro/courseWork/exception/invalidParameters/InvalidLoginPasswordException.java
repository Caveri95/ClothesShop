package ru.skypro.courseWork.exception.invalidParameters;

public class InvalidLoginPasswordException extends InvalidRegistrationParameters {

    public InvalidLoginPasswordException() {
        super("Проверьте правильность ввода логина или пароля");
    }
}
