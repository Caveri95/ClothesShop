package ru.skypro.courseWork.security.service;

import org.springframework.security.core.Authentication;
import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;

import java.nio.file.AccessDeniedException;

public interface SecurityUtils {

    void updatePassword(NewPasswordDto newPasswordDto, Authentication authentication);
}
