package ru.skypro.courseWork.dto;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Класс, представляющий данные для входа аутентифицированного пользователя.
 */
@Data
public class Login {

    /**
     * Пароль пользователя.
     */
    @Size(min = 8, max = 16)
    private String password;

    /**
     * email пользователя.
     */
    @Size(min = 4, max = 32)
    private String username;
}
