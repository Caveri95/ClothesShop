package ru.skypro.courseWork.exception.invalidParameters;

public class InvalidRegistrationParameters extends RuntimeException {

    public InvalidRegistrationParameters(String message) {
        super(message);
    }
}
