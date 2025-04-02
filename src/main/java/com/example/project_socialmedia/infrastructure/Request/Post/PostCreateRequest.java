package com.example.project_socialmedia.infrastructure.Request.Post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostCreateRequest {
    private String content;
    private List<MultipartFile> media;
    private LocalDateTime createPost;
    private LocalDateTime modifiedPost;
}
