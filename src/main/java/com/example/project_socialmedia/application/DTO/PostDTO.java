package com.example.project_socialmedia.application.DTO;

import com.example.project_socialmedia.domain.Model.Comment;
import com.example.project_socialmedia.domain.Model.Like;
import com.example.project_socialmedia.domain.Model.Media;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    // TODO: get userDTO send information into front-end
    private UserDTO user;

    private String content;
    private List<Comment> comments;
    private List<Like> likes;
    private List<MediaDTO> media;
    private LocalDateTime createdPost;
    private LocalDateTime modifiedPost;
}
