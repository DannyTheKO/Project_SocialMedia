package com.project.social_media.controllers.Request.User;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserUpdateRequest {
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String bio;

    @Nullable
    private LocalDateTime birthDate;

    @Nullable
    private MultipartFile profileImage;
    @Nullable
    private MultipartFile bannerImage;

    private String email;
    private String password;
}
