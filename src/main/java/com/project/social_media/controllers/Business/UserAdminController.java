package com.project.social_media.controllers.Business;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.controllers.Request.User.UserUpdateRequest;
import com.project.social_media.domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("${api.prefix}/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserAdminController {
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    /**
     * <h1>POST: Create User (Admin)</h1>
     * <h5>URL: api/v1/admin/users/create</h5>
     * <br>
     *
     * <li>ADMIN: Create User</li>
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

    /**
     * <h1>PUT: Update User</h1>
     * <h5>URL: api/v1/admin/users/user/update?userId=...</h5>
     * <br>
     *
     * <li>ADMIN: Update User By UserID</li>
     *
     * @param request {@link UserUpdateRequest}
     * @return {@link ApiResponse#ApiResponse(String, Object)}
     */
    @PutMapping("/user/update")
    public ResponseEntity<ApiResponse> updateUser(
            @RequestParam Long userId,
            @ModelAttribute UserUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            // Update
            User existingUser = userService.getUserById(userId);
            User updatedUser = userService.updateUser(existingUser.getUserId(), request);
            return ResponseEntity.ok(new ApiResponse("Success", userService.convertToDTO(updatedUser)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR) // 500
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    /**
     * <h1>DELETE: Delete User</h1>
     * <h5>URL: api/v1/admin/users/user/delete?userId=...</h5>
     * <br>
     *
     * <li>ADMIN: Delete User By UserID</li>
     *
     * @param userId {@link User#getUserId()}
     * @return {@link ApiResponse}
     */
    @DeleteMapping("/user/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Long userId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            // Delete
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
