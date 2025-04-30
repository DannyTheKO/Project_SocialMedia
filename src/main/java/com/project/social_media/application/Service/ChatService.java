package com.project.social_media.application.Service;

import com.project.social_media.application.IService.IChatService;
import com.project.social_media.domain.Model.Message;
import com.project.social_media.domain.Repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private static final DateTimeFormatter ISO_8601_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    @Transactional
    @Override
    public Message sendMessage(Message message) {
        String formattedTimestamp = ZonedDateTime.now(ZoneId.of("UTC"))
                .format(ISO_8601_FORMATTER);
        message.setTimestamp(formattedTimestamp);
        Message savedMessage = messageRepository.save(message);
        // Debug log
        System.out.println("Saved message to MongoDB: " + savedMessage);

        // Debug log
        System.out.println("Sending message to sender: /topic/messages/" + message.getSenderId());
        // Send message to sender (SenderId)
        messagingTemplate.convertAndSend("/topic/messages/" + message.getSenderId(), savedMessage);

        // Debug log
        System.out.println("Sending message to receiver: /topic/messages/" + message.getReceiverId());
        // Send message to receiver (ReceiverId)
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), savedMessage);

        return savedMessage;
    }

    @Override
    public Message addUser(Message message) {
        return message;
    }
}
