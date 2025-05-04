package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.NotificationDTO;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.IService.INotificationService;
import com.project.social_media.domain.Model.MongoDB.Notification;
import com.project.social_media.domain.Model.JPA.User;
import com.project.social_media.domain.Repository.MongoDB.NotificationRepository;
import com.project.social_media.domain.Repository.JPA.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Notification getNotificationById(String notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFound("Notification not found"));
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long receiverId) {
        List<Notification> notificationList = notificationRepository.findByReceiverId_OrderByCreatedAtDesc(receiverId);
        return convertToDTOList(notificationList);
    }

    public Long countUnreadNotifications(Long receiverId) {
        List<Notification> notificationList = notificationRepository.findByReceiverId_OrderByCreatedAtDesc(receiverId);
        return notificationList.stream().filter(notification -> !notification.isRead()).count();
    }

    @Override
    public void createFriendRequestNotification(Long senderId, Long receiverId, Long friendRequestId) throws RuntimeException {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new ResourceNotFound("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFound("Receiver not found"));


        if (!Objects.equals(sender.getUserId(), receiver.getUserId())) {
            Notification notification = new Notification();
            notification.setSenderId(sender.getUserId());
            notification.setReceiverId(receiver.getUserId());
            notification.setNotificationEnumType(Notification.NotificationType.FRIEND_REQUEST);
            notification.setContent(sender.getUsername() + " đã gửi yêu cầu kết bạn");
            notification.setRelatedId(friendRequestId);
            notification.setRead(false);
            notification.setCreatedAt(LocalDateTime.now());

            notification = notificationRepository.save(notification);
            sendNotification(notification);
        }
    }

    @Override
    public void createLikePostNotification(Long senderId, Long receiverId, Long postId) throws RuntimeException {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new ResourceNotFound("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFound("Receiver not found"));

        if (!Objects.equals(sender.getUserId(), receiver.getUserId())) {
            Notification notification = new Notification();
            notification.setSenderId(sender.getUserId());
            notification.setReceiverId(receiver.getUserId());
            notification.setNotificationEnumType(Notification.NotificationType.LIKE_POST);
            notification.setContent(sender.getUsername() + " đã thích bài viết của bạn");
            notification.setRelatedId(postId);
            notification.setRead(false);

            notification = notificationRepository.save(notification);
            sendNotification(notification);
        }
    }

    @Override
    public void createCommentPostNotification(Long senderId, Long receiverId, Long commentId) throws RuntimeException {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new ResourceNotFound("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFound("Receiver not found"));

        if (!Objects.equals(sender.getUserId(), receiver.getUserId())) {
            Notification notification = new Notification();
            notification.setSenderId(sender.getUserId());
            notification.setReceiverId(receiver.getUserId());
            notification.setNotificationEnumType(Notification.NotificationType.COMMENT_POST);
            notification.setContent(sender.getUsername() + " đã bình luận bài viết của bạn");
            notification.setRelatedId(commentId);
            notification.setRead(false);

            notification = notificationRepository.save(notification);
            sendNotification(notification);
        }
    }

    @Override
    public void markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFound("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAsReadAll(Long receiverId) {
        List<Notification> notificationList = notificationRepository.findByReceiverId_OrderByCreatedAtDesc(receiverId);
        notificationList.forEach(notification -> notification.setRead(true));
    }

    @Override
    public void deleteNotification(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFound("Notification not found"));

        notificationRepository.delete(notification);
    }

    @Override
    public void deleteAllNotifications(Long receiverId) {
        List<Notification> notificationList = notificationRepository.findByReceiverId_OrderByCreatedAtDesc(receiverId);
        notificationRepository.deleteAll(notificationList);
    }

    private void sendNotification(Notification notification) {
        NotificationDTO notificationDTO = convertToDTO(notification);
        messagingTemplate.convertAndSend("/topic/notification", notificationDTO);
    }

    // Don't use ModelMapper, it's causing problems
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();

        // Set all fields manually to ensure proper type conversion
        notificationDTO.setNotificationId(notification.getId());            // This should be String if using MongoDB
        notificationDTO.setSenderId(notification.getSenderId());
        notificationDTO.setReceiverId(notification.getReceiverId());
        notificationDTO.setContent(notification.getContent());
        notificationDTO.setNotificationEnumType(notification.getNotificationEnumType());
        notificationDTO.setRelatedId(notification.getRelatedId());
        notificationDTO.setIsRead(notification.isRead());
        notificationDTO.setCreatedAt(notification.getCreatedAt());

        return notificationDTO;
    }

    private List<NotificationDTO> convertToDTOList(List<Notification> notificationList) {
        return notificationList.stream().map(this::convertToDTO).toList();
    }
}
