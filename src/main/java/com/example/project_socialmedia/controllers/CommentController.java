package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.DTO.CommentDTO;
import com.example.project_socialmedia.application.Service.CommentService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.controllers.Request.Comment.CommentCreateRequest;
import com.example.project_socialmedia.controllers.Request.Comment.CommentUpdateRequest;
import com.example.project_socialmedia.domain.Model.Comment;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllComment() {
        try {
            List<Comment> commentList = commentService.getAllComments();
            List<CommentDTO> commentDTOList = commentService.convertToDTOList(commentList);
            return ResponseEntity.ok(new ApiResponse("Success", commentDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/all/post")
    public ResponseEntity<ApiResponse> getAllCommentByPostId(
            @RequestParam(required = false) Long postId) {
        try {
            List<Comment> commentList = commentService.getAllCommentsByPostId(postId);
            List<CommentDTO> commentDTOList = commentService.convertToDTOList(commentList);
            return ResponseEntity.ok(new ApiResponse("Success", commentDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/all/user")
    public ResponseEntity<ApiResponse> getAllCommentByUserId(
            @RequestParam(required = false) Long userId) {
        try {
            List<Comment> commentList = commentService.getAllCommentsByUserId(userId);
            List<CommentDTO> commentDTOList = commentService.convertToDTOList(commentList);
            return ResponseEntity.ok(new ApiResponse("Success", commentDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createComment(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @ModelAttribute CommentCreateRequest request) {
        try {
            Comment newComment = commentService.createComment(userId, postId, request);
            CommentDTO newCommentDTO = commentService.convertToDTO(newComment);
            return ResponseEntity.ok(new ApiResponse("Success", newCommentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @PutMapping("/comment/{commentId}/update")
    public ResponseEntity<ApiResponse> updateComment(
            @RequestParam Long userId,
            @PathVariable Long commentId,
            @ModelAttribute CommentUpdateRequest request) {

        try {
            Comment updatedComment = commentService.updateComment(userId, commentId, request);
            CommentDTO updatedCommentDTO = commentService.convertToDTO(updatedComment);
            return ResponseEntity.ok(new ApiResponse("Success", updatedCommentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }


    @DeleteMapping("/comment/delete")
    public ResponseEntity<ApiResponse> deleteComment(
            @RequestParam Long commentId
    ) {

        try {
            commentService.deleteCommentById(commentId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
