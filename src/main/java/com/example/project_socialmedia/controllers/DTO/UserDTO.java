package com.example.project_socialmedia.controllers.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String bio;
    private String email;
    private String profileImageUrl;
    private String bannerImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
