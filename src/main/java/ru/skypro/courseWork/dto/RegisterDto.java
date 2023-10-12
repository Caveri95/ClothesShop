package ru.skypro.courseWork.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * Класс, представляющий DTO (Data Transfer Object) данные для регистрации пользователя.
 */
@Data
public class RegisterDto {
    /**
     * email пользователя.
     */
    @Size(min = 4, max = 32)
    private String username;

    /**
     * Пароль пользователя.
     */
    @Size(min = 8, max = 16)
    private String password;

    /**
     * Имя пользователя.
     */
    @Size(min = 2, max = 16)
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Size(min = 2, max = 16)
    private String lastName;

    /**
     * Контактный телефон пользователя.
     */
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    /**
     * Роль пользователя.
     */
    private Role role;
}
