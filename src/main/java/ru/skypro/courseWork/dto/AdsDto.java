package ru.skypro.courseWork.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {

    private Integer count;

    private List<AdDto> result;
}
