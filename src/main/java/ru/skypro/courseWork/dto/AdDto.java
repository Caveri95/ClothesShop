package ru.skypro.courseWork.dto;

import lombok.Data;
/**
 * Класс, представляющий DTO (Data Transfer Object) объявления.
 */
@Data
public class AdDto {

    /**
     * Идентификатор пользователя.
     */
    private Integer author;

    /**
     * Путь к изображению пользователя.
     */
    private String image;

    /**
     * Идентификатор объявления.
     */
    private Integer pk;

    /**
     * Стоимость товара.
     */
    private Integer price;

    /**
     * Заголовок объявления.
     */
    private String title;
}
