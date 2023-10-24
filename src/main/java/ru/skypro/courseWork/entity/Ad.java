package ru.skypro.courseWork.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс, представляющий сущность объявления в приложении.
 * Соответствует таблице "ad" в базе данных и используется
 * для хранения информации об объявлениях пользователей.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"pk"})
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
}
