package ru.skypro.courseWork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Класс, представляющий список DTO (Data Transfer Object) комментариев.
 */
@Data
@AllArgsConstructor
public class CommentsDto {

    /**
     * Количество комментариев.
     */
    private Integer count;

    /**
     * Список комментариев.
     */
    private List<CommentDto> results;
}
