package com.project.social_media.controllers.Request.Post;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostUpdateRequest {
    @Nullable
    private String content;
    @Nullable
    private List<MultipartFile> files;
}
