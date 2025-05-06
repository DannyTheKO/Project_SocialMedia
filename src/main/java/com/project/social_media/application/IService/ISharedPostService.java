package com.project.social_media.application.IService;

import com.project.social_media.application.DTO.SharedPostDTO;
import com.project.social_media.controllers.Request.SharedPost.SharedPostCreateRequest;
import com.project.social_media.controllers.Request.SharedPost.SharedPostUpdateRequest;
import com.project.social_media.domain.Model.JPA.SharedPost;

import java.util.List;

public interface ISharedPostService {
    List<SharedPost> getAllSharedPosts();
    List<SharedPost> getAllSharedPostsByUserId(Long userId);
    SharedPost getSharedPostById(Long sharedPostId);
    SharedPost createSharedPost(SharedPostCreateRequest request, Long userId);
    SharedPost updateSharedPost(Long userId, Long sharedPostId, SharedPostUpdateRequest request);
    void deleteSharedPost(Long sharedPostId);
    SharedPostDTO convertToDTO(SharedPost sharedPost);
    List<SharedPostDTO> convertToListDTO(List<SharedPost> sharedPosts);
    long countSharesByOriginalPostId(Long originalPostId);
    List<Object> getTimeline();
}