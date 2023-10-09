package ru.skypro.courseWork.entity;

import lombok.Data;

import javax.persistence.*;

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
    private Long createAt;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
}
