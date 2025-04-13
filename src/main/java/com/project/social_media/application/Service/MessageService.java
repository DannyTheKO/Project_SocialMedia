package com.project.social_media.application.Service;

import com.project.social_media.domain.Model.Message;
import com.project.social_media.domain.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesBetweenUsers(Long userId1, Long userId2){
        // debug log
        System.out.println("Fetching messages between " + userId1 + " and " + userId2);

        return
            messageRepository.
                findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(userId1, userId2, userId2,userId1);
    }
}
