package ru.skypro.courseWork.entity;

import lombok.Data;

import javax.persistence.*;
/**
 * Класс, представляющий сущность объявления в приложении.
 * Соответствует таблице "ad" в базе данных и используется
 * для хранения информации об объявлениях пользователей.
 */
@Data
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
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String title;
}
