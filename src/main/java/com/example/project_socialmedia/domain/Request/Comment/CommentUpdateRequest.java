package com.example.project_socialmedia.domain.Request.Comment;

import lombok.Data;

@Data
public class CommentUpdateRequest {
    private Long commentId;
    private String content;
}
