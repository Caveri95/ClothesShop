package ru.skypro.courseWork.dto;

import lombok.Data;

/**
 * Класс, представляющий расширенную информацию об объявлении.
 */
@Data
public class ExtendedAdDto {

    /**
     * Идентификатор объявления.
     */
    private Integer pk;

    /**
     * Имя пользователя.
     */
    private String authorFirstName;

    /**
     * Фамилия пользователя.
     */
    private String authorLastName;

    /**
     * Описание объявления.
     */
    private String description;

    /**
     * email (username) пользователя.
     */
    private String email;

    /**
     * Путь к изображению объявления.
     */
    private String image;

    /**
     * Телефон для связи с пользователем.
     */
    private String phone;

    /**
     * Стоимость товара.
     */
    private Integer price;

    /**
     * Заголовок объявления.
     */
    private String title;
}
