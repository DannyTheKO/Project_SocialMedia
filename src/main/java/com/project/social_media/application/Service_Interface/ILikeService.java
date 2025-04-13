package com.project.social_media.application.Service_Interface;

import com.project.social_media.application.DTO.LikeDTO;
import com.project.social_media.controllers.Request.Like.LikeRequest;
import com.project.social_media.domain.Model.Like;

import java.util.List;

public interface ILikeService {
    List<Like> getAllLikeByPostId(Long postId);
    List<Like> getAllLikeByCommentId(Long commentId);

    Integer getLikeCountByPostId(Long postId);
    Integer getLikeCountByCommentId(Long commentId);

    void toggleLike(Long userId, LikeRequest request);

    List<LikeDTO> convertToDTOList(List<Like> likeList);
}
