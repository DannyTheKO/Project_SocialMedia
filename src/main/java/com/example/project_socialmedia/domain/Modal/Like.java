package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    /**
     * Like Constructor
     * @param likeId        Long
     * @param user          Object: {User}
     * @param post          Object: {Post}
     * @param comment       Object: {Comment}
     * @param createdAt     Date
     */
    public Like(Long likeId, User user, Post post, Comment comment, LocalDateTime createdAt) {
        this.likeId = likeId;
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // ==> Custom Function
}
