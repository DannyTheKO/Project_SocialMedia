package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.DTO.UserDTO;
import com.example.project_socialmedia.application.Service.PostService;
import com.example.project_socialmedia.application.Service.UserService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.controllers.Request.User.UserCreateRequest;
import com.example.project_socialmedia.controllers.Request.User.UserUpdateRequest;
import com.example.project_socialmedia.domain.Model.User;
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
    @PostMapping(value = "/create")
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

    // Update
    @PutMapping(value = "/user/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long userId,
            @ModelAttribute UserUpdateRequest request
    ) {
        try {
            User getUser = userService.getUserById(userId);
            if (getUser != null) {
                User updatedUser = userService.updateUser(userId, request);
                return ResponseEntity.ok(new ApiResponse("Success", userService.convertToDTO(updatedUser)));
            } else {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("User Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }


    // Delete
    @DeleteMapping("/user/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
