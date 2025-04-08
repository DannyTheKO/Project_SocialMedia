package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.ICommentService;
import com.example.project_socialmedia.controllers.Request.Comment.CommentCreateRequest;
import com.example.project_socialmedia.controllers.Request.Comment.CommentUpdateRequest;
import com.example.project_socialmedia.domain.Model.Comment;
import com.example.project_socialmedia.domain.Model.Post;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    private final UserService userService;
    private final PostService postService;

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
        User existingUser = userService.getUserById(userId);

        // Testing
/*
        System.out.println("Retrieved User: " + existingUser.getUserId());
        System.out.println("User Comments: " + existingUser.getComments()
                .stream()
                .map(comment ->
                        "comment: " + comment.getContent() + " created date: " + comment.getCreatedAt())
                .toList()
        );
*/

        return existingUser.getComments();
    }

    /**
     * Get All User Comment From Post
     *
     * @param postId Long
     */
    // TODO: getAllUserCommentsByPostId [Need Testing]
    @Override
    public List<Comment> getAllUserCommentsByPostId(Long postId) {
        Post getPost = postService.getPostById(postId);
        return getPost.getComments();
    }
    /**
     * Add Comment
     *
     * @param request Object {CommentCreateRequest}
     */
    // TODO: addComment [Need Testing]
    @Override
    public Comment addComment(CommentCreateRequest request, Long userId, Long postId) {
        User existingUser = userService.getUserById(userId);
        Post existingPost = postService.getPostById(postId);

        return new Comment(
                existingUser,
                existingPost,
                request.getContent(),
                LocalDateTime.now(),    // Created At
                LocalDateTime.now()     // Updated At
        );
    }

    // TODO: deleteCommentById [Need Testing]

    /**
     * Delete Comment By ID
     *
     * @param commentId Long
     */
    @Override
    public void deleteCommentById(Long commentId) {
        Comment getComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFound("deleteCommentById: commentId not found"));

        commentRepository.delete(getComment);
    }

    // TODO: updateComment [Need Testing]

    /**
     * Update Comment
     *
     * @param request Object {CommentUpdateRequest}
     */
    @Override
    public void updateComment(CommentUpdateRequest request, Long commentId) {
        Comment getComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFound("updateComment: commentId not found"));

        // Change
        getComment.setContent(request.getContent());
        getComment.setUpdatedAt(LocalDateTime.now());

        // Send to Database
        commentRepository.save(getComment);
    }
}
