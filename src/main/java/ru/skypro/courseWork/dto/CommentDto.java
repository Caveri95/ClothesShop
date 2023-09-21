package ru.skypro.courseWork.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Integer author;

    private String authorImage;

    private String authorFirstName;

    private Integer createAt;

    private Integer pk;

    private String text;
}
