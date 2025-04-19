package com.project.social_media.controllers.Business;

import com.project.social_media.application.DTO.CommentDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.ICommentService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Comment.CommentUpdateRequest;
import com.project.social_media.domain.Model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${api.prefix}/admin/comments")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class CommentAdminController {
    private final ICommentService commentService;
    private final IAuthenticationService authenticationService;

    /*
     * DELETE Method
     *
     * Admin operations for comment management
     * Requires ADMIN role authorization
     */

    /**
     * <h1>DELETE: Delete Comment</h1>
     * <h5>URL: api/v1/admin/comments/comment</h5>
     * <br>
     *
     * <li>ADMIN: Delete any comment by specifying the commentId</li>
     * <li>Allows administrators to remove comments regardless of ownership</li>
     *
     * @param commentId The ID of the comment to delete
     * @return {@link ApiResponse} confirming successful deletion
     */
    @DeleteMapping("/comment")
    public ResponseEntity<ApiResponse> deleteComment(@RequestParam Long commentId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            commentService.deleteCommentById(commentId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Success", "Comment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    /**
     * <h1>PUT: Update Comment</h1>
     * <h5>URL: api/v1/admin/comments/comment</h5>
     * <br>
     *
     * <li>ADMIN: Update any comment by specifying the userId, postId, and commentId</li>
     * <li>Allows administrators to modify comment content regardless of ownership</li>
     *
     * @param userId The ID of the user who owns the comment
     * @param postId The ID of the post containing the comment
     * @param commentId The ID of the comment to update
     * @param request {@link CommentUpdateRequest} containing updated comment content
     * @return {@link ApiResponse} containing the updated {@link CommentDTO}
     */
    @PutMapping("/comment")
    public ResponseEntity<ApiResponse> updateComment(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @RequestParam Long commentId,
            @ModelAttribute CommentUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            Comment comment = commentService.updateComment(userId, postId, commentId, request);
            CommentDTO commentDTO = commentService.convertToDTO(comment);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Success", commentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }
}
