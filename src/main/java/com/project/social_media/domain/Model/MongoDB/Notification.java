package com.project.social_media.domain.Model.MongoDB;

import com.project.social_media.domain.Model.JPA.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    private Long senderId;
    private Long receiverId;

    private NotificationType NotificationEnumType;
    private String content;
    private Long relatedId;
    private boolean isRead = false;
    private LocalDateTime createdAt;

    public enum NotificationType{
        FRIEND_REQUEST,
        LIKE_POST,
        LIKE_COMMENT,
        COMMENT_POST,
        REPLY_COMMENT
    }
}


