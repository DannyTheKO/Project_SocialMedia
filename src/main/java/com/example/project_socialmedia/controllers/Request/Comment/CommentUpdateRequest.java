package com.example.project_socialmedia.controllers.Request.Comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentUpdateRequest {
    private String content;
    private LocalDateTime updateAt;
}
