package com.example.project_socialmedia.application.Service;


import com.example.project_socialmedia.application.Service_Interface.IUserService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.controllers.Request.User.UserCreateRequest;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import com.example.project_socialmedia.infrastructure.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserService userService;
    private final UserRepository userRepository;

    // TODO: Add Authentication
    // NOTE: Logout will clear the token on client-side

    public ResponseEntity<ApiResponse> register(UserCreateRequest request) {
        try {
            // Validation
            User checkUsername = userService.getUserByUsername(request.getUsername());
            User checkEmail = userService.getUserByEmail(request.getEmail());

            if (checkUsername != null && checkEmail != null) {
                User createUser = userService.createUser(request);

                String username = createUser.getUsername();
                String email = createUser.getEmail();
                String role = createUser.getUserRole().toString();
                String token = JwtUtil.generateToken(username, email, role);

                return ResponseEntity.ok(new ApiResponse("Success", token));
            }

            return  ResponseEntity.status(UNAUTHORIZED)
                    .body(new ApiResponse("Error", "Username or Email already exists!"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse> login(String username, String password) {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser == null || !existingUser.getPassword().equals(password)) {
            return ResponseEntity.status(UNAUTHORIZED) // Use 401
                    .body(new ApiResponse("Invalid credentials", null)); // Generic message
        }

        String userEmail = existingUser.getEmail();
        String userRole = existingUser.getUserRole().toString();
        existingUser.updateUserLastLogin(LocalDateTime.now()); // Update Last Login Time
        userRepository.save(existingUser);
        String token = JwtUtil.generateToken(username, userEmail, userRole);

        return ResponseEntity.ok(new ApiResponse("Logged in successfully", token));
    }
}
