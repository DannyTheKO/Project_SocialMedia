package com.project.social_media.controllers;

import com.project.social_media.application.Service.ChatService;
import com.project.social_media.domain.Model.MongoDB.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message message) {
        // Debug log
        System.out.println("Received message in ChatController: " + message.getContent());
        chatService.sendMessage(message);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(Message message) {
        chatService.addUser(message);
    }
}
