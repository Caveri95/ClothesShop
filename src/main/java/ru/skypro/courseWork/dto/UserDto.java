package ru.skypro.courseWork.dto;

import lombok.Data;
/**
 * Класс, представляющий DTO (Data Transfer Object) пользователя.
 */
@Data
public class UserDto {

    /**
     * Идентификатор пользователя.
     */
    private Integer id;

    /**
     * email (username) пользователя.
     */
    private String email;

    /**
     * Имя пользователя.
     */
    private String firstName;
    /**
     * Фамилия пользователя.
     */
    private String lastName;

    /**
     * Контактный телефон пользователя.
     */
    private String phone;

    /**
     * Роль пользователя.
     */
    private Role role;

    /**
     * Путь к изображению пользователя.
     */
    private String image;
}
