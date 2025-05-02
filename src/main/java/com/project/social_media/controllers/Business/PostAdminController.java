package com.project.social_media.controllers.Business;

import com.project.social_media.application.DTO.PostDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IPostService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Post.PostUpdateRequest;
import com.project.social_media.domain.Model.JPA.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/admin/posts")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class PostAdminController {
    private final IPostService postService;
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    /**
     * <h1>PUT: Update Post (Admin)</h1>
     * <h5>URL: api/v1/admin/posts/post/{postId}/update?userId=...</h5>
     * <br>
     *
     * <li>ADMIN: Update any user's post by specifying the postId and userId</li>
     * <li>Allows administrators to modify post content regardless of ownership</li>
     * <li>Supports multipart form data for media uploads</li>
     *
     * @param userId The ID of the user who owns the post
     * @param postId The ID of the post to update
     * @param request {@link PostUpdateRequest} containing updated post content and media
     * @return {@link ApiResponse} containing the updated {@link PostDTO}
     */
    @PutMapping(value = "/post/{postId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updatePost(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @ModelAttribute PostUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);

            // Update
            Post updatedPost = postService.updatePost(userId, postId, request);
            PostDTO postDTO = postService.convertToDTO(updatedPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>DELETE: Delete Post (Admin)</h1>
     * <h5>URL: api/v1/admin/posts/post/delete?postId=...</h5>
     * <br>
     *
     * <li>ADMIN: Delete any post by specifying the postId</li>
     * <li>Allows administrators to remove posts regardless of ownership</li>
     *
     * @param postId The ID of the post to delete
     * @return {@link ApiResponse} confirming successful deletion
     */
    @DeleteMapping(value = "/post/delete")
    public ResponseEntity<ApiResponse> deletePost(@RequestParam Long postId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);

            // Delete
            postService.deletePost(postId);
            return ResponseEntity.ok(new ApiResponse("Success", null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
