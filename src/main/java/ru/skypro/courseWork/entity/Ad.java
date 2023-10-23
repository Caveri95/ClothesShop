package ru.skypro.courseWork.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс, представляющий сущность объявления в приложении.
 * Соответствует таблице "ad" в базе данных и используется
 * для хранения информации об объявлениях пользователей.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Ad {

    @Id
    @Column(name = "ad_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(pk, ad.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
