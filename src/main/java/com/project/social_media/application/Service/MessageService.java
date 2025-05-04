package com.project.social_media.application.Service;

import com.project.social_media.application.IService.IMessageService;
import com.project.social_media.domain.Model.MongoDB.Message;
import com.project.social_media.domain.Repository.MongoDB.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getMessagesBetweenUsers(Long userId1, Long userId2) {
        // Debug log
        System.out.println("Fetching messages between " + userId1 + " and " + userId2);

        List<Message> messages = new ArrayList<>();
        messages.addAll(messageRepository.findBySenderIdAndReceiverId_OrderByTimestampAsc(userId1, userId2));
        messages.addAll(messageRepository.findBySenderIdAndReceiverId_OrderByTimestampAsc(userId2, userId1));

        // Sort by timestamp
        messages.sort(Comparator.comparing(Message::getTimestamp));
        return messages;
    }
}
