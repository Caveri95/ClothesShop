package ru.skypro.courseWork.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(columnDefinition = "oid", nullable = false)
    private byte[] data;
}
