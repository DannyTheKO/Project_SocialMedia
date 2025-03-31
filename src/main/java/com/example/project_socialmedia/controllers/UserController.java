package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.Service.PostService;
import com.example.project_socialmedia.application.Service.UserService;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    // TODO: Add User API
    // Read
//    public ResponseEntity<ApiResponse> getAllUser() {
//        List<User> userList = userService.getAllUser();
//    }

    // Create
    // Update
    // Delete
}
