package com.example.project_socialmedia.controllers.Request.Comment;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CommentCreateRequest {
    private String content;
    private List<MultipartFile> mediaFileRequest;
}
