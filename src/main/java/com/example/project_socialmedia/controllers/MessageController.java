package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.domain.Model.Message;
import com.example.project_socialmedia.domain.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/messages")
public class MessageController{

    @Autowired
    private MessageRepository messageRepository;

    // get all messages data between 2 users
    @GetMapping("/{userId1}/{userId2}")
    public List<Message> getMessagesBetweenUsers(
            @PathVariable Long userId1,
            @PathVariable Long userId2) {
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                userId1, userId2, userId2, userId1);
    }

}
