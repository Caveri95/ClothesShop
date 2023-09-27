package ru.skypro.courseWork.exception;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException() {
        super("Комментарий не найден");
    }
}
