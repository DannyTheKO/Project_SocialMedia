package com.example.project_socialmedia.application.Request.Comment;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CommentUpdateRequest {
    private String content;
    private LocalDateTime updateAt;
}
