package com.project.social_media.controllers;

import com.project.social_media.application.DTO.PostDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IPostService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Post.PostCreateRequest;
import com.project.social_media.controllers.Request.Post.PostUpdateRequest;
import com.project.social_media.domain.Model.JPA.Post;
import com.project.social_media.domain.Model.JPA.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {
    private final IPostService postService;
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    /*
     * GET Method
     *
     * All the GET method will be available to public guest.
     *
     * If guest request an action (like create, delete, update Post, Comment, Like... etc.),
     * Will be redirected to front-end login page for authentication
     */

    /**
     * <h1>GET: Get All Posts</h1>
     * <h5>URL: api/v1/posts/all</h5>
     * <br>
     *
     * <li>Retrieve all posts in the system</li>
     *
     * @return {@link ApiResponse} containing a list of {@link PostDTO}
     */
    @GetMapping(value = "/all")
    public ResponseEntity<ApiResponse> getAllPost() {
        try {
            List<Post> postList = postService.getAllPosts();
            List<PostDTO> postDTOList = postService.convertToListDTO(postList);
            return ResponseEntity.ok(new ApiResponse("Success", postDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>GET: Get All Posts By User ID</h1>
     * <h5>URL: api/v1/posts/all/user</h5>
     * <br>
     *
     * <li>Retrieve all posts created by a specific user</li>
     *
     * @param userId The ID of the user whose posts to retrieve
     * @return {@link ApiResponse} containing a list of {@link PostDTO}
     */
    @GetMapping("/all/user")
    public ResponseEntity<ApiResponse> getAllPostByUserId(
            @RequestParam(required = false) Long userId) {
        try {
            List<Post> existingPost = postService.getAllPostsByUserId(userId);
            List<PostDTO> postDTOList = postService.convertToListDTO(existingPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>GET: Get Post By ID</h1>
     * <h5>URL: api/v1/posts/post</h5>
     * <br>
     *
     * <li>Retrieve a specific post by its ID</li>
     *
     * @param postId The ID of the post to retrieve
     * @return {@link ApiResponse} containing the requested {@link PostDTO}
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);
            if (post != null) {
                PostDTO postDTO = postService.convertToDTO(post);
                return ResponseEntity.ok(new ApiResponse("Success", postDTO));
            }

            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Post not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /*
     * POST Method
     *
     * Action that required user information will have to authenticate
     * Otherwise will be redirected to log in page for authentication
     */

    /**
     * <h1>POST: Create Post</h1>
     * <h5>URL: api/v1/posts/create</h5>
     * <br>
     *
     * <li>Creates a new post with the authenticated user as the author</li>
     * <li>Supports multipart form data for media uploads</li>
     *
     * @param request {@link PostCreateRequest} containing post content and media
     * @return {@link ApiResponse} containing the created {@link PostDTO}
     */
    @PostMapping(value = "/post/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createPost(@ModelAttribute PostCreateRequest request) {
        try {
            // Get Authentication User
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            // Create
            Post newPost = postService.createPost(request, authUser.getUserId());
            PostDTO postDTO = postService.convertToDTO(newPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /*
     * PUT Method
     *
     * Action that required user information will have to authenticate
     * Otherwise will be redirected to log in page for authentication
     */

    /**
     * <h1>PUT: Update Post</h1>
     * <h5>URL: api/v1/posts/post/{postId}/update</h5>
     * <br>
     *
     * <li>Updates an existing post owned by the authenticated user</li>
     * <li>Validates that the current user is the owner of the post</li>
     * <li>Supports multipart form data for media uploads</li>
     *
     * @param postId The ID of the post to update
     * @param request {@link PostUpdateRequest} containing updated post content and media
     * @return {@link ApiResponse} containing the updated {@link PostDTO}
     */
    @PutMapping(value = "/post/{postId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updatePost(
            @PathVariable Long postId,
            @ModelAttribute PostUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);
            Post existingPost = postService.getPostById(postId);

            // Authentication
            if (!authUser.getUserId().equals(existingPost.getUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Update
            Post updatedPost = postService.updatePost(authUser.getUserId(), postId, request);
            PostDTO postDTO = postService.convertToDTO(updatedPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /*
     * DELETE Method
     *
     * Action that required user information will have to authenticate
     * Otherwise will be redirected to log in page for authentication
     */

    /**
     * <h1>DELETE: Delete Post</h1>
     * <h5>URL: api/v1/posts/post/delete</h5>
     * <br>
     *
     * <li>Deletes an existing post owned by the authenticated user</li>
     * <li>Validates that the current user is the owner of the post</li>
     *
     * @param postId The ID of the post to delete
     * @return {@link ApiResponse} confirming successful deletion
     */
    @DeleteMapping(value = "/post/delete")
    public ResponseEntity<ApiResponse> deletePost(@RequestParam Long postId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);
            Post existingPost = postService.getPostById(postId);

            // Authentication
            if (!authUser.getUserId().equals(existingPost.getUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Delete
            postService.deletePost(postId);
            return ResponseEntity.ok(new ApiResponse("Success", null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
