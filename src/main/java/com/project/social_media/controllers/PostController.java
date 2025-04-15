package com.project.social_media.controllers;

import com.project.social_media.application.DTO.PostDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IPostService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Post.PostCreateRequest;
import com.project.social_media.controllers.Request.Post.PostUpdateRequest;
import com.project.social_media.domain.Model.Post;
import com.project.social_media.domain.Model.User;
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

    @PostMapping(value = "/post/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createPost(@ModelAttribute PostCreateRequest request) {
        try {
            // Get Authentication User
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            // Create
            Post newPost = postService.createPost(request, authUser.getUserId());
            PostDTO postDTO = postService.convertToDTO(newPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }


    // TODO:
    //  updatePost is an "Admin" role action,
    //  create a separate API endpoint that correspond with authenticated "User" role
    @PutMapping(value = "/post/{postId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updatePost(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @ModelAttribute PostUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());
            Post existingPost = postService.getPostById(postId);

            // Authentication
            if (!authUser.getUserId().equals(existingPost.getUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Update
            Post updatedPost = postService.updatePost(userId, postId, request);
            PostDTO postDTO = postService.convertToDTO(updatedPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @DeleteMapping(value = "/post/delete")
    public ResponseEntity<ApiResponse> deletePost(@RequestParam Long postId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());
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
