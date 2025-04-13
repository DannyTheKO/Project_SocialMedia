package com.project.social_media.application.Service;

import com.project.social_media.application.Service_Interface.IChatService;
import com.project.social_media.domain.Model.Message;
import com.project.social_media.domain.Repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements IChatService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Transactional
    @Override
    public Message sendMessage(Message message) {
        message.setTimestamp(new java.util.Date().toString());
        Message savedMessage = messageRepository.save(message);

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
