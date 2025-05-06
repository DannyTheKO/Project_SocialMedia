package com.project.social_media.controllers.Request.Like;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeRequest {
    @Nullable
    private Long postId;
    @Nullable
    private Long commentId;
    @Nullable
    private Long sharedPostId;

    private LocalDateTime createdAt;
}
