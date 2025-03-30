package com.example.project_socialmedia.infrastructure.Request.Comment;

import lombok.Data;

import java.sql.Date;

@Data
public class CommentCreateRequest {
    private String content;
}
