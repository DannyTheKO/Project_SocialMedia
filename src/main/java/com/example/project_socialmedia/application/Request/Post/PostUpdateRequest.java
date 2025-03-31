package com.example.project_socialmedia.application.Request.Post;

import lombok.Data;

import java.util.List;

@Data
public class PostUpdateRequest {
    private String content;
    private List<String> mediaUrls;
}
