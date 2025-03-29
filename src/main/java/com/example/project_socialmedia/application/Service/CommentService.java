package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Service_Interface.ICommentService;
import com.example.project_socialmedia.domain.Modal.Comment;
import com.example.project_socialmedia.domain.Modal.Post;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.domain.Repository.CommentRepository;
import com.example.project_socialmedia.domain.Repository.PostRepository;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import com.example.project_socialmedia.domain.Request.Comment.CommentCreateRequest;
import com.example.project_socialmedia.domain.Request.Comment.CommentUpdateRequest;
import com.example.project_socialmedia.infrastructure.Exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * Get Comment By ID
     *
     * @param id Long
     * @return Object {Comment}
     */
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("getCommentById: commentId not found"));
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
     * Get All User Comments By User ID
     *
     * @param userId Long
     * @return List{Object}: {Comment}
     */
    @Override
    public List<Comment> getAllUserCommentsByUserId(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("getAllUserCommentsByUserId: userId not found"));

        // Testing
        System.out.println("Retrieved User: " + existingUser.getUserId());
        System.out.println("User Comments: " + existingUser.getComments()
                .stream()
                .map(comment ->
                        "comment: " + comment.getContent() + " created date: " + comment.getCreatedAt())
                .toList()
        );

        return existingUser.getComments();
    }

    /**
     * TODO: Add Comment [NEED TESTING]
     *
     * @param request Object {CommentCreateRequest}
     */
    @Override
    public Comment addComment(CommentCreateRequest request, Long userId, Long postId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("addComment: userId not found"));

        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("addComment: postId not found"));

        return new Comment(
                existingUser,
                existingPost,
                request.getContent(),
                LocalDateTime.now()
        );
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
