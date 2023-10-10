package ru.skypro.courseWork.exception.forbiddenException;

public class AdForbiddenException extends ForbiddenException {

    public AdForbiddenException() {
        super("Недостаточно прав для редактирования объявления");
    }
}
