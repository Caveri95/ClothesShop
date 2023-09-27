package ru.skypro.courseWork.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserDto {

    @Size(min = 3, max = 10)
    private String firstName;

    @Size(min = 3, max = 10)
    private String lastname;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}
