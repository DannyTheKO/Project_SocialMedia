package com.example.project_socialmedia.controllers.Request.Media;

import com.example.project_socialmedia.domain.Model.Post;
import lombok.Data;

@Data
public class CreateMediaRequest {
    private String url;
    private String fileType;
    private String fileName;
    private Post post;
}
