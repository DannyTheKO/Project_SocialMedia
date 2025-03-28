package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Service_Interface.ICommentService;
import com.example.project_socialmedia.domain.Modal.Comment;
import com.example.project_socialmedia.domain.Repository.CommentRepository;
import com.example.project_socialmedia.domain.Request.Comment.CommentCreateRequest;
import com.example.project_socialmedia.domain.Request.Comment.CommentUpdateRequest;
import com.example.project_socialmedia.infrastructure.Exception.CommentNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    /**
     * Get Comment By ID
     *
     * @param id Long
     * @return Object {Comment}
     */
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound("getCommentById: commentId not found"));
    }

    /**
     * Get All Comment
     *
     * @return List{Object}: {Comment}
     */
    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    /**
     * TODO: Get All User Comments By User ID
     *
     * @param userId Long
     * @return List{Object}: {Comment}
     */
    @Override
    public List<Comment> getAllUserCommentsByUserId(Long userId) {
        return List.of();
    }

    /**
     * TODO: Add Comment
     *
     * @param request Object {CommentCreateRequest}
     */
    @Override
    public void addComment(CommentCreateRequest request) {

    }

    /**
     * TODO: Delete Comment By ID
     *
     * @param id Long
     */
    @Override
    public void deleteCommentById(Long id) {

    }

    /**
     * TODO: Update Comment
     *
     * @param request Object {CommentUpdateRequest}
     */
    @Override
    public void updateComment(CommentUpdateRequest request) {

    }
}
