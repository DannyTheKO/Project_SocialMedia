package com.project.social_media.domain.Repository.MongoDB;

import com.project.social_media.domain.Model.MongoDB.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByReceiverId_OrderByCreatedAtDesc(Long userId);
    List<Notification> findBySenderId_OrderByCreatedAtDesc(Long userId);
}
