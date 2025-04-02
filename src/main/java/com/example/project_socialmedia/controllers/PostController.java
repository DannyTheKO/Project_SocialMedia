package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.DTO.PostDTO;
import com.example.project_socialmedia.application.Service.PostService;
import com.example.project_socialmedia.application.Service.UserService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.domain.Modal.Post;
import com.example.project_socialmedia.infrastructure.Request.Post.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @return      Object {PostDTO}
     */
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

//    @PostMapping("/post/create")
//    public ResponseEntity<ApiResponse> createPost(Long userId, PostCreateRequest request) {
//        Post newPost = postService.createPost(request, userId);
//    }

}
