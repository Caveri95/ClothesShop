package ru.skypro.courseWork.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * Класс, представляющий данные для обновления информации у авторизованного пользователя.
 */
@Data
public class UpdateUserDto {

    /**
     * Имя пользователя.
     */
    @Size(min = 3, max = 10)
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Size(min = 3, max = 10)
    private String lastName;

    /**
     * Контактный телефон пользователя
     */
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}
