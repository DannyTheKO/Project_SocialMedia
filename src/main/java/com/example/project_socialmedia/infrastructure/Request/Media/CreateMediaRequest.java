package com.example.project_socialmedia.infrastructure.Request.Media;

import com.example.project_socialmedia.domain.Modal.Post;
import lombok.Data;

@Data
public class CreateMediaRequest {
    private String url;
    private String fileType;
    private String fileName;
    private Post post;
}
