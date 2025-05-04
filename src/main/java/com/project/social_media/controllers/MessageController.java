package com.project.social_media.controllers;

import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IMessageService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.domain.Model.MongoDB.Message;
import com.project.social_media.domain.Model.JPA.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/messages")
public class MessageController {
    private final IMessageService messageService;
    private final IAuthenticationService authenticationService;
    private final IUserService userService;

    // TODO: Message Controller

    @GetMapping("/all/{userId1}/{userId2}")
    private List<Message> getMessageBetweenUsers(
            @PathVariable Long userId1,
            @PathVariable Long userId2)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authenticationService.checkValidationAuth(authentication);
        User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

        assert authUser != null;
        return messageService.getMessagesBetweenUsers(userId1, userId2);
    }
}
