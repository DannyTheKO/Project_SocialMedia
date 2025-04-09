package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.domain.Model.Like;

import java.util.List;

public interface ILikeService {
    public List<Like> getAllLikeByPostId(Long postId);
    public List<Like> getAllLikeByCommentId(Long commentId);

}
