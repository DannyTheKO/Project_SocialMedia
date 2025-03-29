package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    // ==> Entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdPost;

    @Column(nullable = false)
    private LocalDateTime modifiedPost;

    private String imageUrl;
    private String videoUrl;


    public Post(User user, List<Comment> comments, List<Like> likes, String content, String imageUrl, String videoUrl, LocalDateTime createdPost, LocalDateTime modifiedPost) {
        this.user = user;
        this.comments = comments;
        this.likes = likes;
        this.content = content;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.createdPost = createdPost;
        this.modifiedPost = modifiedPost;
    }

    // ==> Custom Function
}
