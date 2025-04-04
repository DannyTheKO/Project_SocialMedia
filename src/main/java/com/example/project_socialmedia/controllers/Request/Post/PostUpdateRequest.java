package com.example.project_socialmedia.controllers.Request.Post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostUpdateRequest {
    private String content;
    private List<MultipartFile> media;
}
