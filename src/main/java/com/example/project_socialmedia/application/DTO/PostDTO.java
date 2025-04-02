package com.example.project_socialmedia.application.DTO;

import com.example.project_socialmedia.domain.Modal.Comment;
import com.example.project_socialmedia.domain.Modal.Like;
import com.example.project_socialmedia.domain.Modal.Media;
import com.example.project_socialmedia.domain.Modal.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private User user;
    private List<Comment> comments;
    private List<Like> likes;
    private List<Media> media;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
