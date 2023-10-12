package ru.skypro.courseWork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * Класс, представляющий список DTO (Data Transfer Object) объявлений.
 */
@Data
@AllArgsConstructor
public class AdsDto {

    /**
     * Количество объявлений.
     */
    private Integer count;

    /**
     * Список объявлений.
     */
    private List<AdDto> results;
}
