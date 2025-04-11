package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.application.DTO.LikeDTO;
import com.example.project_socialmedia.controllers.Request.Like.LikeRequest;
import com.example.project_socialmedia.domain.Model.Like;

import java.util.List;

public interface ILikeService {
    List<Like> getAllLikeByPostId(Long postId);
    List<Like> getAllLikeByCommentId(Long commentId);

    Integer getLikeCountByPostId(Long postId);
    Integer getLikeCountByCommentId(Long commentId);

    void toggleLike(Long userId, LikeRequest request);

    List<LikeDTO> convertToDTOList(List<Like> likeList);
}
