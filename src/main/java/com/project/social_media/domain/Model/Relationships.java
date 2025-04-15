package com.project.social_media.domain.Model;

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
@Table(name = "relationships",
       indexes = {
            @Index(name = "idx_users", columnList = "user_id_1, user_id_2"),

            // index tăng tốc lấy danh sách bạn bè của một người dùng
            @Index(name = "idx_user1_status", columnList = "user_id_1, status")
       })

public class Relationships {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Long relationshipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_1", nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_2", nullable = false)
    private User user2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RelationshipStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Relationship Constructor
     *
     * @param user1     Object: {User}
     * @param user2     Object: {User}
     * @param status    Enum: {RelationshipStatus}
     * @param createdAt LocalDateTime
     * @param updatedAt LocalDateTime
     */


    public Relationships(User user1, User user2, RelationshipStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // ==> Custom Function
    public void updateRelationshipStatus(RelationshipStatus status, LocalDateTime updatedAt) {
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public enum RelationshipStatus {
        FRIENDS,
        BLOCKED
    }

}
