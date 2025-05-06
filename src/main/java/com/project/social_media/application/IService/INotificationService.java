package com.project.social_media.application.IService;

import com.project.social_media.application.DTO.NotificationDTO;
import com.project.social_media.domain.Model.MongoDB.Notification;

import java.util.List;

public interface INotificationService {
    Notification getNotificationById(String notificationId);

    List<NotificationDTO> getUserNotifications(Long receiverId);

    Long countUnreadNotifications(Long receiverId);

    void createFriendRequestNotification(Long senderId, Long receiverId, Long relationshipId);

    void createLikePostNotification(Long senderId, Long receiverId, Long postId);

    void createCommentPostNotification(Long senderId, Long receiverId, Long commentId);

    void markAsRead(String notificationId);

    void markAsReadAll(Long userId);

    void deleteNotification(String notificationId);

    void deleteAllNotifications(Long receiverId);

    void sendNotificationToUser(Notification notification);

    void sendNotificationCountToUser(Long receiverId);
}
