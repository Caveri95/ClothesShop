package ru.skypro.courseWork.dto;

import lombok.Data;

import javax.validation.constraints.Size;
/**
 * Класс, представляющий данные для создания пароля пользователя.
 */
@Data
public class NewPasswordDto {

    /**
     * Текущий пароль пользователя.
     */
    @Size(min = 8, max = 16)
    private String currentPassword;

    /**
     * Новый пароль пользователя.
     */
    @Size(min = 8, max = 16)
    private String newPassword;
}
