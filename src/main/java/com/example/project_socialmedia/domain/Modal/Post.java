package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


    public Post(User user, String content, LocalDateTime createdPost, LocalDateTime modifiedPost) {
        this.user = user;
        this.content = content;
        this.createdPost = createdPost;
        this.modifiedPost = modifiedPost;
    }

    // ==> Custom Function
}
