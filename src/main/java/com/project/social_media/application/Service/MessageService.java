package com.project.social_media.application.Service;

import com.project.social_media.application.IService.IMessageService;
import com.project.social_media.domain.Model.Message;
import com.project.social_media.domain.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> getMessagesBetweenUsers(Long userId1, Long userId2) {
        // Debug log
        System.out.println("Fetching messages between " + userId1 + " and " + userId2);

        List<Message> messages = new ArrayList<>();
        messages.addAll(messageRepository.findBySenderIdAndReceiverIdOrderByTimestampAsc(userId1, userId2));
        messages.addAll(messageRepository.findBySenderIdAndReceiverIdOrderByTimestampAsc(userId2, userId1));

        // Sort by timestamp
        messages.sort(Comparator.comparing(Message::getTimestamp));
        return messages;
    }
}
