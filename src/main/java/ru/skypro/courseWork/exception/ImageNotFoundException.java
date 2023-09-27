package ru.skypro.courseWork.exception;

public class ImageNotFoundException extends NotFoundException{
    public ImageNotFoundException() {
        super("Изображение не найдено");
    }
}
