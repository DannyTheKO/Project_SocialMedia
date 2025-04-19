package com.project.social_media.application.Service;

import com.project.social_media.application.IService.INotficationService;
import com.project.social_media.domain.Model.Notification;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.NotificationRepository;
import com.project.social_media.domain.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotficationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createFriendRequestNotification(Long senderId, Long receiverId, Long friendRequestId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setType(Notification.NotificationType.FRIEND_REQUEST);
        notification.setContent(sender.getUsername() + " đã gửi yêu cầu kết bạn");
        notification.setRelatedId(friendRequestId);
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    @Override
    public void createLikePostNotification(Long senderId, Long receiverId, Long postId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setType(Notification.NotificationType.LIKE_POST);
        notification.setContent(sender.getUsername() + " đã thích bài viết của bạn");
        notification.setRelatedId(postId);
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    @Override
    public void createCommentPostNotification(Long senderId, Long receiverId, Long commentId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setType(Notification.NotificationType.COMMENT_POST);
        notification.setContent(sender.getUsername() + " đã bình luận bài viết của bạn");
        notification.setRelatedId(commentId);
        notification.setRead(false);

        notificationRepository.save(notification);
    }
}
