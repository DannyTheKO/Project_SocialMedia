package com.project.social_media.controllers.Request.Post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostCreateRequest {
    private String content;

//    Em sửa tên chỗ này để frontend gửi trùng key lên nhe anh :3
//    private List<MultipartFile> mediaFileRequest;
    private List<MultipartFile> files;
}
