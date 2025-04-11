package com.example.project_socialmedia.application.DTO;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeDTO {
    private Long likeId;

    private Long userId;
    private String username;
    private String firstname;
    private String lastname;

    @Nullable
    private Long postId;
    @Nullable
    private Long commentId;

    private LocalDateTime createdAt;
}
