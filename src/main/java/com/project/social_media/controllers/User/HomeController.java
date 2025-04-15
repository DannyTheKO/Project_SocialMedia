package com.project.social_media.controllers.User;

import com.project.social_media.application.DTO.PostDTO;
import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IPostService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Post.PostCreateRequest;
import com.project.social_media.controllers.Request.Post.PostUpdateRequest;
import com.project.social_media.domain.Model.Post;
import com.project.social_media.domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/home")
public class HomeController {
    private final IUserService userService;
    private final IPostService postService;
    private final IAuthenticationService authenticationService;

    // TODO: Home Controller
    /*
    * This part of the controller will be our "Home" default route site
    */


    /*
     * GET Method
     *
     * All the GET method will be available to public guest.
     *
     * If guest request an action (like create, delete, update Post, Comment, Like... etc.),
     * Will be redirected to front-end login page,
     */

    /**
     * <h1> GET: whoAmI </h1>
     * <h5> URL: api/v1/home/whoAmI </h5>
     * <br>
     * <li>
     * This is a GET request that verify token string to User exist in the database
     * and return JSON user DTO information of the current authenticated user
     * </li>
     *
     * <li> It required Header to be contained with </li>
     * <p> {@code "Authorization Bearer" : "token"} </p>
     */
    @GetMapping("/whoAmI")
    public ResponseEntity<ApiResponse> whoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authenticationService.authenticationCheck(authentication);

        // Check Flag
        try {
            User authUser = userService.getUserByUsername(authentication.getName());
            UserDTO userDTO = userService.convertToDTO(authUser);

            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Authentication Failed", e.getMessage()));
        }
    }

    /**
     * <h1> GET: Get All Posts from database </h1>
     * <h5> URL: api/v1/home/posts </h5>
     * <br>
     *
     * <li>
     * This request will get all Post from the database
     * Convert Post to DTO and sent it to the front-end
     * </li>
     *
     * <li>User don't need to be authenticated</li>
     */
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse> allPosts() {
        try {
            List<Post> postList = postService.getAllPosts();
            List<PostDTO> postDTOList = postService.convertToListDTO(postList);
            return ResponseEntity.ok(new ApiResponse("Success", postDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    /*
     * POST Method
     *
     * Action that required user information will have to authenticate
     * Otherwise will be redirected to log in page for authentication
     */

    /**
     * <h1> POST: Create Post </h1>
     * <h5> URL: api/v1/home/post/create </h5>
     * <br>
     *
     * <li> Create Post, only authenticated User </li>
     *
     * @param request {@link PostCreateRequest} Object
     */
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

    /*
     * PUT Method
     *
     * Action that required user information will have to authenticate
     * Otherwise will be redirected to log in page for authentication
     */

    /**
     * <h1> PUT: Update Post </h1>
     * <h5> URL: api/v1/home/post/update/{postId} </h5>
     * <br>
     *
     * <li> Update Post, only for authenticated user</li>
     *
     * @param postId Long
     * @param request {@link PostUpdateRequest} Object
     * @return {@link ApiResponse}
     */
    @PutMapping(value = "/post/update/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updatePost(
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
            Post updatedPost = postService.updatePost(authUser.getUserId(), postId, request);
            PostDTO postDTO = postService.convertToDTO(updatedPost);
            return ResponseEntity.ok(new ApiResponse("Success", postDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

}
