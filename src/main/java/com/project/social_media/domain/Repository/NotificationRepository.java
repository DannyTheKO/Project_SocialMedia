package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverUserIdOrderByCreatedAtDesc(Long receiverId);

    List<Notification> findByReceiverUserIdAndIsReadFalseOrderByCreatedAtDesc(Long receiverId);
}
