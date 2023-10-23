package ru.skypro.courseWork.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс, представляющий сущность комментариев в приложении.
 * Соответствует таблице "comment" в базе данных и используется
 * для хранения информации о комментариях пользователей.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(pk, comment.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
