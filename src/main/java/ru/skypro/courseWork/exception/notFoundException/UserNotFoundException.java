package ru.skypro.courseWork.exception.notFoundException;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
