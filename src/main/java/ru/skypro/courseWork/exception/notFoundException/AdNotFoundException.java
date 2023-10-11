package ru.skypro.courseWork.exception.notFoundException;

public class AdNotFoundException extends NotFoundException{
    public AdNotFoundException() {
        super("Объявление не найдено");
    }
}
