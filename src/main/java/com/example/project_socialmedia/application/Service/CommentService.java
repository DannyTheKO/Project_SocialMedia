package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.DTO.CommentDTO;
import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.ICommentService;
import com.example.project_socialmedia.controllers.Request.Comment.CommentCreateRequest;
import com.example.project_socialmedia.controllers.Request.Comment.CommentUpdateRequest;
import com.example.project_socialmedia.domain.Model.Comment;
import com.example.project_socialmedia.domain.Model.Post;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    private final UserService userService;
    private final PostService postService;

    private final ModelMapper modelMapper;

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
    public List<Comment> getAllCommentsByUserId(Long userId) {
        User existingUser = userService.getUserById(userId);
        return existingUser.getComments();
    }

    /**
     * Get All User Comment From Post
     *
     * @param postId Long
     */
    @Override
    public List<Comment> getAllCommentsByPostId(Long postId) {
        Post getPost = postService.getPostById(postId);
        return getPost.getComments();
    }

    /**
     * Add Comment
     *
     * @param request Object {CommentCreateRequest}
     */
    @Override
    public Comment createComment(Long userId, Long postId, CommentCreateRequest request) {
        try {
            User existingUser = userService.getUserById(userId);
            Post existingPost = postService.getPostById(postId);

            return new Comment(
                    existingUser,
                    existingPost,
                    request.getContent(),
                    LocalDateTime.now(),    // Created At
                    LocalDateTime.now()     // Updated At
            );

            //TODO: handle media

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public Comment updateComment(Long userId, Long commentId, CommentUpdateRequest request) {
        Comment getComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFound("updateComment: commentId not found"));

        // Change
        getComment.setContent(request.getContent());
        getComment.setUpdatedAt(LocalDateTime.now());

        // TODO: handle media change if requested


        // Send to Database
        commentRepository.save(getComment);
        return getComment;
    }

    public CommentDTO convertToDTO(Comment comment) {
        CommentDTO mappedDTO = modelMapper.map(comment, CommentDTO.class);

        // Set PostId
        mappedDTO.setPostId(comment.getPost().getPostId());

        // Set Like

        return mappedDTO;
    }

    public List<CommentDTO> convertToDTOList(List<Comment> commentList) {
        return commentList.stream().map(this::convertToDTO).toList();
    }
}
