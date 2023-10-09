package ru.skypro.courseWork.security.service;

import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;

import java.nio.file.AccessDeniedException;

public interface SecurityUtils {
    void checkAccessToComment(Comment comment);

    void checkAccessToAd(Ad ad);

    void updatePassword(NewPasswordDto newPasswordDto);
}
