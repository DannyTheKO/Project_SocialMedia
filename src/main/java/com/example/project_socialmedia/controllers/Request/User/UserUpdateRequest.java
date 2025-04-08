package com.example.project_socialmedia.controllers.Request.User;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class UserUpdateRequest {
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String bio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Nullable
    private Date birthDate;

    @Nullable
    private MultipartFile profileImage;
    @Nullable
    private MultipartFile bannerImage;

    private String email;
    private String password;
}
