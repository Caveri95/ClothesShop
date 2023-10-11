package ru.skypro.courseWork.exception.notFoundException;

public class ImageNotFoundException extends NotFoundException{
    public ImageNotFoundException() {
        super("Изображение не найдено");
    }
}
