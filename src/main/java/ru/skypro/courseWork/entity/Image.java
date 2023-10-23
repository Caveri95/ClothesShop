package ru.skypro.courseWork.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс, представляющий сущность изображений в приложении.
 * Соответствует таблице "image" в базе данных и используется
 * для хранения изображений пользователей и объявлений.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
