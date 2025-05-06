package com.project.social_media.domain.Model.JPA;

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
@Table(name = "shared_posts")
public class SharedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sharedPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_post_id", nullable = false)
    private Post originalPost; 

    @Column(name = "shared_content", length = 1000)
    private String sharedContent; 

    @Column(name = "shared_at", nullable = false)
    private LocalDateTime sharedAt;

    @PrePersist
    public void prePersist() {
        if (sharedAt == null) {
            sharedAt = LocalDateTime.now();
        }
    }

    @OneToMany(mappedBy = "sharedPost", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "sharedPost", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likes;
}
