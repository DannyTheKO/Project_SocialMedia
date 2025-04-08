package com.example.project_socialmedia.controllers.Request.Comment;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CommentUpdateRequest {
    @Nullable
    private String content;
    @Nullable
    private List<MultipartFile> mediaFileRequest;
}
