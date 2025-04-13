package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.Service.MessageService;
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
    private MessageService messageService;

    @GetMapping("/{userId1}/{userId2}")
    private List<Message> getMessageBetweenUsers(
            @PathVariable Long userId1,
            @PathVariable Long userId2){
        return messageService.getMessagesBetweenUsers(userId1, userId2);
    }

}
