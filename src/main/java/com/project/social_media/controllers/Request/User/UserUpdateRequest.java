package com.project.social_media.controllers.Request.User;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String bio;
    private LocalDateTime birthDate;

    private MultipartFile profileImage;
    private MultipartFile bannerImage;

    private String email;
    private String password;
}
