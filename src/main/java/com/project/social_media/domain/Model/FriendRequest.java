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
@Table(name = "friends_request",
       indexes = {
            @Index(name = "idx_from_to", columnList = "from_user_id, to_user_id"),

            // index tăng tốc lấy danh sách yêu cầu kết bạn đến một người dùng
            @Index(name = "idx_to_user_status", columnList = "to_user_id, status")
       })

public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "friend_request_id")
    private Long friendRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendRequestStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * FriendRequest Constructor
     *
     * @param fromUser Object: {User}
     * @param toUser   Object: {User}
     * @param status   Enum: {FriendRequestStatus}
     * @param createdAt LocalDateTime
     */

    public FriendRequest(User fromUser, User toUser, FriendRequestStatus status, LocalDateTime createdAt) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
        this.createdAt = createdAt;
    }

    public enum FriendRequestStatus{
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
