package com.yerim.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @Builder(builderClassName = "PostRegister", builderMethodName = "postRegister")
    public Post(User user, String title, String text, LocalDateTime createAt) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.createAt = createAt;
        this.lastUpdatedAt = null;
    }

}
