package com.project.social_media.application.IService;

import com.project.social_media.application.DTO.CommentDTO;
import com.project.social_media.controllers.Request.Comment.CommentCreateRequest;
import com.project.social_media.controllers.Request.Comment.CommentUpdateRequest;
import com.project.social_media.domain.Model.JPA.Comment;

import java.util.List;

public interface ICommentService {

    /**
     * Get Comment By ID
     *
     * @param id Long
     * @return Object {Comment}
     */
    Comment getCommentById(Long id);

    /**
     * Get All Comment
     *
     * @return List{Object}: {Comment}
     */
    List<Comment> getAllComments();

    /**
     * Get All User Comments By User ID
     *
     * @param userId Long
     * @return List{Object}: {Comment}
     */
    List<Comment> getAllCommentsByUserId(Long userId);

    /**
     * Get All User Comment From Post
     */
    List<Comment> getAllCommentsByPostId(Long postId);

    /**
     * Add Comment
     *
     * @param request Object {CommentCreateRequest}
     */
    Comment createComment(Long userId, Long postId, CommentCreateRequest request);

    /**
     * Delete Comment By ID
     *
     * @param id Long
     */
    void deleteCommentById(Long id);

    /**
     * Update Comment
     *
     * @param request Object {CommentUpdateRequest}
     */
    Comment updateComment(Long userId, Long postId, Long commentId, CommentUpdateRequest request);

    CommentDTO convertToDTO(Comment comment);

    List<CommentDTO> convertToDTOList(List<Comment> commentList);
}
