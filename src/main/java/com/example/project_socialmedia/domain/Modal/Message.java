package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;


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

    private String content;
    private Boolean readStatus;
    private Date createdAt;

    /**
     * Message Constructor
     * @param messageId     Long
     * @param sender        Object: {User}
     * @param receiver      Object: {User}
     * @param content       String
     * @param readStatus    Boolean
     * @param createdAt     Date
     */
    public Message(Long messageId, User sender, User receiver, String content, Boolean readStatus, Date createdAt) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.readStatus = readStatus;
        this.createdAt = createdAt;
    }

    // ==> Custom Function

}
