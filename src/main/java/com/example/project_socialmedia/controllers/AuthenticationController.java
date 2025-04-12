package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.Service_Interface.IAuthenticationService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.controllers.Request.User.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@ModelAttribute UserCreateRequest request) {
        try {
            String token = authenticationService.register(request);
            if (token != null) {
                return ResponseEntity.ok(new ApiResponse("Success", token));
            }

            return ResponseEntity.ok(new ApiResponse("Failed", "Invalid Credentials"));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestPart String username, @RequestPart String password) {
        try {
            String token = authenticationService.login(username, password);
            if (token != null) {
                return ResponseEntity.ok(new ApiResponse("Success", token));
            }

            return ResponseEntity.ok(new ApiResponse("Failed", "Invalid Credentials"));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new ApiResponse("Error", e.getMessage()));
        }

    }
}
