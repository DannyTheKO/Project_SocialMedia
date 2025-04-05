package com.example.project_socialmedia.application.DTO;

import com.example.project_socialmedia.domain.Model.Comment;
import com.example.project_socialmedia.domain.Model.Like;
import com.example.project_socialmedia.domain.Model.Media;
import com.example.project_socialmedia.domain.Model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private String content;
    private List<Comment> comments;
    private List<Like> likes;
    private List<Media> media;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
