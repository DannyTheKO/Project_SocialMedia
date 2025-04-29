package com.project.social_media.controllers;

import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IMessageService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.application.Service.MessageService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.domain.Model.Message;
import com.project.social_media.domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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
        authenticationService.authenticationCheck(authentication);
        User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

        assert authUser != null;
        return messageService.getMessagesBetweenUsers(userId1, userId2);
    }
}
