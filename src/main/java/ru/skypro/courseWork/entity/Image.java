package ru.skypro.courseWork.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс, представляющий сущность изображений в приложении.
 * Соответствует таблице "image" в базе данных и используется
 * для хранения изображений пользователей и объявлений.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] data;


}
