package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.DTO.UserDTO;
import com.example.project_socialmedia.application.Service.PostService;
import com.example.project_socialmedia.application.Service.UserService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.infrastructure.Config.Request.User.UserCreateRequest;
import com.example.project_socialmedia.infrastructure.Config.Request.User.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    /**
     * Get All User
     *
     * @return Object {userDTOList}
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUser() {
        try {
            List<User> userList = userService.getAllUser();
            List<UserDTO> userDTOList = userService.convertToDTOList(userList);
            return ResponseEntity.ok(new ApiResponse("Success!", userDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

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

    // Create
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreateRequest request) {
        try {
            User createUser = userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse("Success", createUser));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // Update
    @PutMapping("/user/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request) {

        try {
            User getUser = userService.getUserById(userId);
            if (getUser == null) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("User Not Found", null));
            }

            User updatedUser = userService.updateUser(userId, request);
            return ResponseEntity.ok(new ApiResponse("Success", userService.convertToDTO(updatedUser)));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // Delete
}
