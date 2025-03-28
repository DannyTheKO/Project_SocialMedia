package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

import java.sql.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    private String content;
    private Date createdComment;

    /**
     * Comment Constructor
     * @param commentId         Long
     * @param user              Object: {User}
     * @param post              Object: {Post}
     * @param likes             List Object: List[{Like}]
     * @param content           String
     * @param createdComment    Date
     */
    public Comment(Long commentId, User user, Post post, List<Like> likes, String content, Date createdComment) {
        this.commentId = commentId;
        this.user = user;
        this.post = post;
        this.likes = likes;
        this.content = content;
        this.createdComment = createdComment;
    }

    // Custom Function
}
