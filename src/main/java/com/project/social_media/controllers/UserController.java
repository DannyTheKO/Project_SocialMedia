package com.project.social_media.controllers;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.Service_Interface.IAuthenticationService;
import com.project.social_media.application.Service_Interface.IUserService;
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
            return ResponseEntity.ok(new ApiResponse("Success", userDTOList));
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

    // Update
    @PutMapping("/user/update")
    public ResponseEntity<ApiResponse> updateUser(
            @RequestParam Long userId,
            @ModelAttribute UserUpdateRequest request
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            String authUser = authentication.getName();
            User existingUser = userService.getUserById(userId);

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


    // Delete
    @DeleteMapping("/user/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Long userId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            String authUser = authentication.getName();
            User existingUser = userService.getUserById(userId);

            // Authentication
            if (!authUser.equals(existingUser.getUsername())) {
                return ResponseEntity.status(FORBIDDEN)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Delete
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
