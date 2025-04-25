package com.project.social_media.application.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
    private Long postId;
    private Long commentId;
    private Long userId;

    private String username;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MediaDTO> media;
    private List<LikeDTO> likes;
}
