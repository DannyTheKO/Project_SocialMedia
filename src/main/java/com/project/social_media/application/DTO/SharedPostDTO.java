package com.project.social_media.application.DTO;

import com.project.social_media.domain.Model.JPA.Post;
import lombok.Data;

import java.util.List;

@Data
public class SharedPostDTO {
    private Long sharedPostId;
    private UserDTO user;
    private PostDTO originalPost;
    private String sharedContent;
    private String sharedAt;
    private List<CommentDTO> comments;
    private List<LikeDTO> likes;
    private List<MediaDTO> media;
}
