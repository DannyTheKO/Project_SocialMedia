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
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_1", nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_2", nullable = false)
    private User user2;
    @Column(nullable = false)
    private Enum<friendStatus> statusEnum;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Friend Constructor
     *
     * @param user1      Object: {User}
     * @param user2      Object: {User}
     * @param statusEnum ENUM: {PENDING, ACCEPTED, DECLINED}
     * @param createdAt  LocalDateTime
     */
    public Friend(User user1, User user2, Enum<friendStatus> statusEnum, LocalDateTime createdAt) {
        this.user1 = user1;
        this.user2 = user2;
        this.statusEnum = statusEnum;
        this.createdAt = createdAt;
    }

    public enum friendStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }

    // ==> Custom Function
}
