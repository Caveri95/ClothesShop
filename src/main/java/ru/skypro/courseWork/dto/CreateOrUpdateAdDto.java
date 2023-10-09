package ru.skypro.courseWork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CreateOrUpdateAdDto {

    @Size(min = 4, max = 32)
    private String title;

    @Min(0)
    @Max(10_000_000)
    private Integer price;

    @Size(min = 8, max = 64)
    private String description;
}
