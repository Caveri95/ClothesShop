package ru.skypro.courseWork.entity;

import lombok.Data;

import javax.persistence.*;
/**
 * Класс, представляющий сущность комментариев в приложении.
 * Соответствует таблице "comment" в базе данных и используется
 * для хранения информации о комментариях пользователей.
 */
@Data
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
}
