package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    // Find all messages between 2 users ( two ways: user1 -> user2 & user2 -> user1)
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            Long senderId, Long receiverId, Long receiverId2, Long senderId2);
}
