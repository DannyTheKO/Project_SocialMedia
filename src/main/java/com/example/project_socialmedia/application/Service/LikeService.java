package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Service_Interface.ILikeService;
import com.example.project_socialmedia.domain.Model.Like;

import java.util.List;

public class LikeService implements ILikeService {
    /**
     * @param postId
     * @return
     */
    @Override
    public List<Like> getAllLikeByPostId(Long postId) {
        return List.of();
    }

    /**
     * @param commentId
     * @return
     */
    @Override
    public List<Like> getAllLikeByCommentId(Long commentId) {
        return List.of();
    }
}
