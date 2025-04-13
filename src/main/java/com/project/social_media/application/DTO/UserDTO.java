package com.project.social_media.application.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private String email;
    //private String password;
    private String profileImageUrl;
    private String bannerImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
