package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_1", nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_2", nullable = false)
    private User user2;

    public enum friendStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    };

    private Enum<friendStatus> statusEnum;

    /**
     * Friend Constructor
     * @param id            Long
     * @param user1         Object: {User}
     * @param user2         Object: {User}
     * @param statusEnum    ENUM: {PENDING, ACCEPTED, DECLINED}
     */
    public Friend(Long id, User user1, User user2, Enum<friendStatus> statusEnum) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.statusEnum = statusEnum;
    }

    // ==> Custom Function

}
