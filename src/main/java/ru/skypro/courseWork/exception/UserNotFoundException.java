package ru.skypro.courseWork.exception;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
