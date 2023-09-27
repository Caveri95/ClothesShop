package ru.skypro.courseWork.exception;

public class AdNotFoundException extends NotFoundException{
    public AdNotFoundException() {
        super("Объявление не найдено");
    }
}
