package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.DTO.PostDTO;
import com.example.project_socialmedia.application.Service.PostService;
import com.example.project_socialmedia.application.Service.UserService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.domain.Model.Post;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.controllers.Request.Post.PostCreateRequest;
import com.example.project_socialmedia.controllers.Request.Post.PostUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    // TODO: PostController

    /**
     * Get All Post
     *
     * @return Object {PostDTO}
     */
    @Operation
    @GetMapping("/all")
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

    @Operation
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

    @Operation
    @PostMapping(value = "/user/{userId}/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createPost(
            @PathVariable Long userId,
            @RequestPart("content") String content,
            @RequestPart(value = "media", required = false) List<MultipartFile> mediaFiles) {
        try {
            User getUser = userService.getUserById(userId);
            if (getUser != null) {
                // Create PostCreateRequest
                PostCreateRequest request = new PostCreateRequest();
                request.setContent(content);
                request.setMedia(mediaFiles);

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


    @Operation
    @PutMapping("/post/{postId}/update")
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
}
