package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.domain.Modal.Comment;
import com.example.project_socialmedia.domain.Request.Comment.CommentCreateRequest;
import com.example.project_socialmedia.domain.Request.Comment.CommentUpdateRequest;

import java.util.List;

public interface ICommentService {

    /**
     * Get Comment By ID
     * @param id    Long
     * @return      Object {Comment}
     */
    Comment getCommentById(Long id);

    /**
     * Get All Comment
     * @return      List{Object}: {Comment}
     */
    List<Comment> getAllComments();

    /**
     * Get All User Comments By User ID
     * @param userId    Long
     * @return          List{Object}: {Comment}
     */
    List<Comment> getAllUserCommentsByUserId(Long userId);

    /**
     * Add Comment
     * @param request Object {CommentCreateRequest}
     */
    Comment addComment (CommentCreateRequest request, Long userId, Long postId);

    /**
     * Delete Comment By ID
     * @param id    Long
     */
    void deleteCommentById(Long id);

    /**
     * Update Comment
     * @param request   Object {CommentUpdateRequest}
     */
    void updateComment(CommentUpdateRequest request);
}
