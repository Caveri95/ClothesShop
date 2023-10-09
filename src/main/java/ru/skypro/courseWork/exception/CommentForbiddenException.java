package ru.skypro.courseWork.exception;

public class CommentForbiddenException extends ForbiddenException {

    public CommentForbiddenException() {
        super("Недостаточно прав для редактирования комментария");
    }
}
