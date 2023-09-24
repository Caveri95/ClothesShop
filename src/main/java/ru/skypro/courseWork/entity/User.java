package ru.skypro.courseWork.entity;

import lombok.Data;
import ru.skypro.courseWork.dto.Role;

@Data
public class User {

    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private Role role;

    private String image;
}
