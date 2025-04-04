package com.example.project_socialmedia.controllers.Request.User;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String bio;
    private MultipartFile profileImage;
    private MultipartFile bannerImage;
    private Timestamp birthDate;
}
