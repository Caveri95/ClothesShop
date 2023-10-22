package ru.skypro.courseWork.exception.notFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException() {
        super("Комментарий не найден");
    }
}
