package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.domain.Model.Message;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.MessageRepository;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class ChatController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    @Transactional
    public Message sendMessage(Message message) {
        message.setTimestamp(new java.util.Date().toString());

        return messageRepository.save(message);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(Message message) {
        return message;
    }
}
