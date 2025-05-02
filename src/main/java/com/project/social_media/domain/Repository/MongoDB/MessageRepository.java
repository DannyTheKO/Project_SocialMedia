package com.project.social_media.domain.Repository.MongoDB;

import com.project.social_media.domain.Model.MongoDB.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    // Find all messages between 2 users ( two ways: user1 -> user2 & user2 -> user1)
    List<Message> findBySenderIdAndReceiverIdOrderByTimestampAsc(Long senderId, Long receiverId);
}
