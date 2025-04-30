package com.project.social_media.application.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserDTO {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private LocalDateTime birthDate;
    private String profileImageUrl;
    private String bannerImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
