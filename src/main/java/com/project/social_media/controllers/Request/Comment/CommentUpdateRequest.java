package com.project.social_media.controllers.Request.Comment;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CommentUpdateRequest {
    private String content;
    private List<MultipartFile> mediaFileRequest;
}
