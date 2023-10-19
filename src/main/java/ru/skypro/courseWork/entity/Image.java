package ru.skypro.courseWork.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
/**
 * Класс, представляющий сущность изображений в приложении.
 * Соответствует таблице "image" в базе данных и используется
 * для хранения изображений пользователей и объявлений.
 */
@Data
@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    //@Column(columnDefinition = "oid", nullable = false)  потому что Н2 ругается в тестах
    private byte[] data;
}
