package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.Service.ChatService;
import com.example.project_socialmedia.domain.Model.Message;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.MessageRepository;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message message) {
        chatService.sendMessage(message);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(Message message) {
        chatService.addUser(message);
    }
}
