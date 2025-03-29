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
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_user_id")
    private User receiver;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean readStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Message Constructor
     * @param messageId     Long
     * @param sender        Object: {User}
     * @param receiver      Object: {User}
     * @param content       String
     * @param readStatus    Boolean
     * @param createdAt     LocalDateTime
     */
    public Message(Long messageId, User sender, User receiver, String content, Boolean readStatus, LocalDateTime createdAt) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.readStatus = readStatus;
        this.createdAt = createdAt;
    }

    // ==> Custom Function

}
