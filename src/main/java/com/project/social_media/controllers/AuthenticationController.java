package com.project.social_media.controllers;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.Service.UserService;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Authentication.LoginRequest;
import com.project.social_media.controllers.Request.Authentication.TokenForm;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/whoAmI")
    public ResponseEntity<ApiResponse> whoAmI() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            User user = userService.getUserByUsername(authentication.getName());
            UserDTO userDTO = userService.convertToDTO(user);

            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserCreateRequest request) {
        try {
            TokenForm token = authenticationService.register(request);
            if (token.getStatus().equals(Boolean.FALSE)) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("Failed", token));
            }

            return ResponseEntity.ok(new ApiResponse("Success", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            TokenForm token = authenticationService.login(request);
            if (token.getStatus().equals(Boolean.FALSE)) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("Failed", token));
            }

            return ResponseEntity.ok(new ApiResponse("Success", token));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new ApiResponse("Error", e.getMessage()));
        }
    }
}
