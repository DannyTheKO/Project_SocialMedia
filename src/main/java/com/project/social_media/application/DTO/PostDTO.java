package com.project.social_media.application.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private Long postId;
    private String content;
    private LocalDateTime createdPost;
    private LocalDateTime modifiedPost;
    private UserDTO user;

    private List<MediaDTO> media;
    private List<CommentDTO> comments;
    private List<LikeDTO> likes;
    private Long shareCount;
}
