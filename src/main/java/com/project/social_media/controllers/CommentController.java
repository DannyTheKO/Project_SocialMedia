package com.project.social_media.controllers;

import com.project.social_media.application.DTO.CommentDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.ICommentService;
import com.project.social_media.application.IService.IPostService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Comment.CommentCreateRequest;
import com.project.social_media.controllers.Request.Comment.CommentUpdateRequest;
import com.project.social_media.domain.Model.Comment;
import com.project.social_media.domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/comments")
public class CommentController {
    private final ICommentService commentService;
    private final IUserService  userService;
    private final IPostService postService;
    private final IAuthenticationService authenticationService;

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
    public ResponseEntity<ApiResponse> getAllCommentByPostId(@RequestParam(required = false) Long postId) {
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
    public ResponseEntity<ApiResponse> getAllCommentByUserId(@RequestParam(required = false) Long userId) {
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
            @RequestParam Long postId,
            @ModelAttribute CommentCreateRequest request) {
        try {
            // Get authenticate user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            // Create
            Comment newComment = commentService.createComment(authUser.getUserId(), postId, request);
            CommentDTO newCommentDTO = commentService.convertToDTO(newComment);
            return ResponseEntity.ok(new ApiResponse("Success", newCommentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @PutMapping("/comment/{commentId}/update")
    public ResponseEntity<ApiResponse> updateComment(
            @RequestParam Long postId,
            @PathVariable Long commentId,
            @ModelAttribute CommentUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());
            Comment existingComment = commentService.getCommentById(commentId);

            // Authentication Check
            if (!authUser.getUserId().equals(existingComment.getUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Update
            Comment updatedComment = commentService.updateComment(authUser.getUserId(), postId, commentId, request);
            CommentDTO updatedCommentDTO = commentService.convertToDTO(updatedComment);
            return ResponseEntity.ok(new ApiResponse("Success", updatedCommentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }


    @DeleteMapping("/comment/delete")
    public ResponseEntity<ApiResponse> deleteComment(@RequestParam Long commentId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User user = userService.getUserByUsername(authentication.getName());
            Comment existingComment = commentService.getCommentById(commentId);

            // Authentication Check
            if (!user.getUserId().equals(existingComment.getUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Delete
            commentService.deleteCommentById(commentId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
