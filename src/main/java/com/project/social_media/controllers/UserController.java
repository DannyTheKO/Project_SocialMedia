package com.project.social_media.controllers;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.controllers.Request.User.UserUpdateRequest;
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
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    /*
     * GET Method
     *
     * All the GET method will be available to public guest.
     *
     * If guest request an action (like create, delete, update Post, Comment, Like... etc.),
     * Will be redirected to front-end login page,
     */

    /**
     * <h1>GET: Get All User</h1>
     * <h5>URL: api/v1/users/all</h5>
     * <br>
     *
     * <li>Retrieve all users from database</li>
     *
     * @return {@link ApiResponse#ApiResponse(String, Object)}
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUser() {
        try {
            List<User> userList = userService.getAllUser();
            List<UserDTO> userDTOList = userService.convertToDTOList(userList);
            return ResponseEntity.ok(new ApiResponse("Success", userDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>GET: Get User By ID</h1>
     * <h5>URL: api/v1/users/user/{userId}</h5>
     * <br>
     *
     * <li>Retrieve single user by ID</li>
     * @param userId {@link User#getUserId()}
     * @return {@link ApiResponse}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User getUser = userService.getUserById(userId);
            UserDTO userDTO = userService.convertToDTO(getUser);
            if (getUser != null) {
                return ResponseEntity.ok(new ApiResponse("Success", userDTO));
            }

            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("User Not Found", null));

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
     * <h1>POST: Create User</h1>
     * <h5>URL: api/vi/users/create</h5>
     * <br>
     *
     * @deprecated
     * <li>Should be an Admin action</li>
     * <li>This {@link AuthenticationController#register} API endpoint is more suitable for user</li>
     *
     * @param request {@link UserCreateRequest}
     * @return {@link ApiResponse#ApiResponse(String, Object)}
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreateRequest request) {
        try {
            User createUser = userService.createUser(request);
            UserDTO userDTO = userService.convertToDTO(createUser);
            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
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
     * <h1>PUT: Update User</h1>
     * <h5>URL: api/vi/users/user/update</h5>
     * <br>
     *
     * <li>This action should only update the current user, not other</li>
     *
     * @param request {@link UserUpdateRequest}
     * @return {@link ApiResponse#ApiResponse(String, Object)}
     */
    @PutMapping("/user/update")
    public ResponseEntity<ApiResponse> updateUser(@ModelAttribute UserUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            String authUser = authentication.getName();
            User existingUser = userService.getUserByUsername(authUser).orElse(null);

            // Authentication
            if (!authUser.equals(existingUser.getUsername())) {
                return ResponseEntity.status(FORBIDDEN) // 403
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Update
            User updatedUser = userService.updateUser(existingUser.getUserId(), request);
            return ResponseEntity.ok(new ApiResponse("Success", userService.convertToDTO(updatedUser)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR) // 500
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
     * <h1>DELETE: Delete User</h1>
     * <h5>URL: api/vi/users/user/delete</h5>
     * <br>
     *
     * <li> Delete User self, prevent other</li>
     *
     * @return {@link ApiResponse}
     */
    @DeleteMapping("/user/delete")
    public ResponseEntity<ApiResponse> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            String authUser = authentication.getName();
            User existingUser = userService.getUserByUsername(authUser).orElse(null);

            // Delete
            userService.deleteUser(existingUser.getUserId());
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
