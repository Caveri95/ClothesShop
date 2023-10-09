package ru.skypro.courseWork.exception;

public class AdForbiddenException extends ForbiddenException {

    public AdForbiddenException() {
        super("Недостаточно прав для редактирования объявления");
    }
}
