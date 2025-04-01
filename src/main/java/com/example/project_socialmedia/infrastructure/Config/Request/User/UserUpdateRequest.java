package com.example.project_socialmedia.infrastructure.Config.Request.User;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String bio;
    private MultipartFile profileImageUrl;
    private MultipartFile bannerImageUrl;
    private LocalDateTime birthDate;
}
