package ru.skypro.courseWork.dto;

import lombok.Data;

/**
 * Класс, представляющий DTO (Data Transfer Object) комментария.
 */
@Data
public class CommentDto {
    /**
     * Идентификатор пользователя.
     */
    private Integer author;

    /**
     * Путь к изображению пользователя.
     */
    private String authorImage;

    /**
     * Имя пользователя.
     */
    private String authorFirstName;

    /**
     * Время создания комментария в миллисекундах с 00:00 1 января 1970.
     */
    private Long createdAt;

    /**
     * Идентификатор комментария.
     */
    private Integer pk;

    /**
     * Содержание комментария.
     */
    private String text;
}
