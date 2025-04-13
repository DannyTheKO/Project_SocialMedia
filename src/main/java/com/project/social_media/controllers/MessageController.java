package com.project.social_media.controllers;

import com.project.social_media.application.Service.MessageService;
import com.project.social_media.domain.Model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/messages")
public class MessageController {
    private MessageService messageService;

    // TODO: Message Controller

    @GetMapping("/{userId1}/{userId2}")
    private List<Message> getMessageBetweenUsers(
            @PathVariable Long userId1,
            @PathVariable Long userId2) {
        return messageService.getMessagesBetweenUsers(userId1, userId2);
    }

}
