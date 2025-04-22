package com.project.social_media.application.IService;

public interface INotificationService {
    void createFriendRequestNotification(Long senderId, Long receiverId, Long friendRequestId);

    void createLikePostNotification(Long senderId, Long receiverId, Long postId);

    void createCommentPostNotification(Long senderId, Long receiverId, Long commentId);
}
