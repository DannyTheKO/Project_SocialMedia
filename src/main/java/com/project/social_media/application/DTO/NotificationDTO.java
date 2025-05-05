package com.project.social_media.application.DTO;

import com.project.social_media.domain.Model.MongoDB.Notification;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private String notificationId;

    private Long senderId;
    private String senderName;
    private String senderImageUrl;

    private Long receiverId;

    private Notification.NotificationType NotificationEnumType;

    private String content;
    private Long relatedId;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
