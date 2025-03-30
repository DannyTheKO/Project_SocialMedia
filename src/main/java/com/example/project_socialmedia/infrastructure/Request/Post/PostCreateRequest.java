package com.example.project_socialmedia.infrastructure.Request.Post;

import lombok.Data;

import java.util.List;

@Data
public class PostCreateRequest {
    private String content;
    private List<String> mediaUrls;
}
