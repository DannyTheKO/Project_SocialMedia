package com.project.social_media.controllers;

import com.project.social_media.application.DTO.SharedPostDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IPostService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.application.IService.ISharedPostService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.SharedPost.SharedPostCreateRequest;
import com.project.social_media.controllers.Request.SharedPost.SharedPostUpdateRequest;
import com.project.social_media.domain.Model.JPA.SharedPost;
import com.project.social_media.domain.Model.JPA.User;
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
@RequestMapping("${api.prefix}/shared-posts")
public class SharedPostController {

    private final ISharedPostService sharedPostService;
    private final IPostService postService;
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    /**
     * <h1>GET: Get All Shared Posts</h1>
     * <h5>URL: api/v1/shared-posts/all</h5>
     * <br>
     *
     * <li>Retrieve all shared posts in the system</li>
     *
     * @return {@link ApiResponse} containing a list of {@link SharedPostDTO}
     */
    @GetMapping(value = "/all")
    public ResponseEntity<ApiResponse> getAllSharedPosts() {
        try {
            List<SharedPost> sharedPostList = sharedPostService.getAllSharedPosts();
            List<SharedPostDTO> sharedPostDTOList = sharedPostService.convertToListDTO(sharedPostList);
            return ResponseEntity.ok(new ApiResponse("Success", sharedPostDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>GET: Get All Shared Posts By User ID</h1>
     * <h5>URL: api/v1/shared-posts/all/user</h5>
     * <br>
     *
     * <li>Retrieve all shared posts created by a specific user</li>
     *
     * @param userId The ID of the user whose shared posts to retrieve
     * @return {@link ApiResponse} containing a list of {@link SharedPostDTO}
     */
    @GetMapping("/all/user")
    public ResponseEntity<ApiResponse> getAllSharedPostsByUserId(
            @RequestParam(required = false) Long userId) {
        try {
            List<SharedPost> existingSharedPosts = sharedPostService.getAllSharedPostsByUserId(userId);
            List<SharedPostDTO> sharedPostDTOList = sharedPostService.convertToListDTO(existingSharedPosts);
            return ResponseEntity.ok(new ApiResponse("Success", sharedPostDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>GET: Get Shared Post By ID</h1>
     * <h5>URL: api/v1/shared-posts/post/{sharedPostId}</h5>
     * <br>
     *
     * <li>Retrieve a specific shared post by its ID</li>
     *
     * @param sharedPostId The ID of the shared post to retrieve
     * @return {@link ApiResponse} containing the requested {@link SharedPostDTO}
     */
    @GetMapping("/post/{sharedPostId}")
    public ResponseEntity<ApiResponse> getSharedPostById(@PathVariable Long sharedPostId) {
        try {
            SharedPost sharedPost = sharedPostService.getSharedPostById(sharedPostId);
            if (sharedPost != null) {
                SharedPostDTO sharedPostDTO = sharedPostService.convertToDTO(sharedPost);
                return ResponseEntity.ok(new ApiResponse("Success", sharedPostDTO));
            }
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Shared post not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>POST: Create Shared Post</h1>
     * <h5>URL: api/v1/shared-posts/post/create</h5>
     * <br>
     *
     * <li>Creates a new shared post with the authenticated user as the sharer</li>
     *
     * @param request {@link SharedPostCreateRequest} containing original post ID and shared content
     * @return {@link ApiResponse} containing the created {@link SharedPostDTO}
     */
    @PostMapping(value = "/post/create")
    public ResponseEntity<ApiResponse> createSharedPost(@RequestBody SharedPostCreateRequest request) {
        try {
            // Get Authentication User
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            // Create
            SharedPost newSharedPost = sharedPostService.createSharedPost(request, authUser.getUserId());
            SharedPostDTO sharedPostDTO = sharedPostService.convertToDTO(newSharedPost);
            return ResponseEntity.ok(new ApiResponse("Success", sharedPostDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>PUT: Update Shared Post</h1>
     * <h5>URL: api/v1/shared-posts/post/{sharedPostId}/update</h5>
     * <br>
     *
     * <li>Updates an existing shared post owned by the authenticated user</li>
     * <li>Validates that the current user is the owner of the shared post</li>
     *
     * @param sharedPostId The ID of the shared post to update
     * @param request {@link SharedPostUpdateRequest} containing updated shared content
     * @return {@link ApiResponse} containing the updated {@link SharedPostDTO}
     */
    @PutMapping(value = "/post/{sharedPostId}/update")
    public ResponseEntity<ApiResponse> updateSharedPost(
            @PathVariable Long sharedPostId,
            @RequestBody SharedPostUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);
            SharedPost existingSharedPost = sharedPostService.getSharedPostById(sharedPostId);

            // Authentication
            if (!authUser.getUserId().equals(existingSharedPost.getUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Update
            SharedPost updatedSharedPost = sharedPostService.updateSharedPost(authUser.getUserId(), sharedPostId, request);
            SharedPostDTO sharedPostDTO = sharedPostService.convertToDTO(updatedSharedPost);
            return ResponseEntity.ok(new ApiResponse("Success", sharedPostDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>DELETE: Delete Shared Post</h1>
     * <h5>URL: api/v1/shared-posts/post/delete</h5>
     * <br>
     *
     * <li>Deletes an existing shared post owned by the authenticated user</li>
     * <li>Validates that the current user is the owner of the shared post</li>
     *
     * @param sharedPostId The ID of the shared post to delete
     * @return {@link ApiResponse} confirming successful deletion
     */
    @DeleteMapping(value = "/post/delete")
    public ResponseEntity<ApiResponse> deleteSharedPost(@RequestParam Long sharedPostId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);
            SharedPost existingSharedPost = sharedPostService.getSharedPostById(sharedPostId);

            // Authentication
            if (!authUser.getUserId().equals(existingSharedPost.getUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Delete
            sharedPostService.deleteSharedPost(sharedPostId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>GET: Count Shares By Original Post ID</h1>
     * <h5>URL: api/v1/shared-posts/count/{originalPostId}</h5>
     * <br>
     *
     * <li>Count the number of shares for a specific original post</li>
     *
     * @param originalPostId The ID of the original post
     * @return {@link ApiResponse} containing the count of shares
     */
    @GetMapping("/count/{originalPostId}")
    public ResponseEntity<ApiResponse> countSharesByOriginalPostId(@PathVariable Long originalPostId) {
        try {
            long shareCount = sharedPostService.countSharesByOriginalPostId(originalPostId);
            return ResponseEntity.ok(new ApiResponse("Success", shareCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>GET: Get Timeline (Posts and Shared Posts)</h1>
     * <h5>URL: api/v1/shared-posts/timeline</h1>
     * <br>
     *
     * <li>Retrieve a combined list of posts and shared posts for the authenticated user</li>
     *
     * @return {@link ApiResponse} containing a list of combined DTOs (PostDTO and SharedPostDTO)
     */
    @GetMapping("/timeline")
    public ResponseEntity<ApiResponse> getTimeline() {
        try {
            List<Object> timeline = sharedPostService.getTimeline();
            return ResponseEntity.ok(new ApiResponse("Success", timeline));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}