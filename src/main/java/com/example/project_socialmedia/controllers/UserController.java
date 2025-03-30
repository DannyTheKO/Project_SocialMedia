package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.Service.PostService;
import com.example.project_socialmedia.application.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    // TODO: Add User API
    // Create
    // Read
    // Update
    // Delete
}
