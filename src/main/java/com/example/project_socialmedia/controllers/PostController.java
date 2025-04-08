package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.DTO.PostDTO;
import com.example.project_socialmedia.application.Service.PostService;
import com.example.project_socialmedia.application.Service.UserService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.controllers.Request.Post.PostCreateRequest;
import com.example.project_socialmedia.controllers.Request.Post.PostUpdateRequest;
import com.example.project_socialmedia.domain.Model.Post;
import com.example.project_socialmedia.domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    // PostController

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

    @PostMapping(value = "/user/{userId}/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createPost(
            @PathVariable Long userId,
            @ModelAttribute PostCreateRequest request) {
        try {
            User getUser = userService.getUserById(userId);
            if (getUser != null) {
                Post newPost = postService.createPost(request, userId);
                PostDTO postDTO = postService.convertToDTO(newPost);
                return ResponseEntity.ok(new ApiResponse("Success", postDTO));
            }
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("userId not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }


    @PutMapping(value = "/post/{postId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updatePost(Long userId, @PathVariable Long postId, PostUpdateRequest request) {
        try {
            Post updatedPost = postService.updatePost(userId, postId, request);
            PostDTO postDTO = postService.convertToDTO(updatedPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @DeleteMapping(value = "/post/{postId}/delete")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        try {
            Post existingPost = postService.getPostById(postId);
            if (existingPost != null) {
                postService.deletePost(postId);
                return ResponseEntity.ok(new ApiResponse("Success", null));
            }

            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Post not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
