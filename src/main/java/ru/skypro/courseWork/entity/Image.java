package ru.skypro.courseWork.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(nullable = false)
    private byte[] data;
}
