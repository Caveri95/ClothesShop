package ru.skypro.courseWork.dto;

import lombok.Data;

import javax.validation.constraints.Size;
/**
 * Класс, представляющий данные для создания или обновления комментария.
 */
@Data
public class CreateOrUpdateCommentDto {

    /**
     * Содержание комментария.
     */
    @Size(min = 8, max = 64)
    private String text;
}
