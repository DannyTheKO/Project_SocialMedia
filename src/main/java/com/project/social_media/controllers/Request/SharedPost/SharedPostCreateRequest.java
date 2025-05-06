package com.project.social_media.controllers.Request.SharedPost;

import lombok.Data;

@Data
public class SharedPostCreateRequest {
    private Long originalPostId;
    private String sharedContent;
}
