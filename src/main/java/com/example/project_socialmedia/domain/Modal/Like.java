package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
     *
     * @param user      Object: {User}
     * @param post      Object: {Post}
     * @param comment   Object: {Comment}
     * @param createdAt Date
     */
    public Like(User user, Post post, Comment comment, LocalDateTime createdAt) {
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // ==> Custom Function
}
