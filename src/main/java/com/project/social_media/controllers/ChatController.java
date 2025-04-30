package com.project.social_media.controllers;

import com.project.social_media.application.Service.ChatService;
import com.project.social_media.domain.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message message) {
        // Debug log
        System.out.println("Received message in ChatController: " + message);
        chatService.sendMessage(message);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(Message message) {
        chatService.addUser(message);
    }
}
