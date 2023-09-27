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

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image authorImage;

    @Column(nullable = false)
    private String authorFirstName;

    @Column(nullable = false)
    private Long createAt;

    @Column(nullable = false)
    private String text;
}
