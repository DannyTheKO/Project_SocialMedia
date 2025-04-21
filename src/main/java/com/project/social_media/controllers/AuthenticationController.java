package com.project.social_media.controllers;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.IService.IRefreshTokenService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Authentication.LoginRequest;
import com.project.social_media.controllers.Request.Authentication.RefreshTokenRequest;
import com.project.social_media.controllers.Request.Authentication.TokenForm;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.domain.Model.RefreshToken;
import com.project.social_media.domain.Model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    private final IUserService userService;
    private final IRefreshTokenService refreshTokenService;

    /*
     * GET Method
     *
     * User information endpoints require authentication
     */

    /**
     * <h1>GET: Who Am I</h1>
     * <h5>URL: api/v1/auth/whoAmI</h5>
     * <br>
     *
     * <li>Returns information about the currently authenticated user</li>
     * <li>Requires a valid authentication token</li>
     *
     * @return {@link ApiResponse} containing current user information
     */
    @GetMapping("/whoAmI")
    public ResponseEntity<ApiResponse> whoAmI() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);

            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Error", "User not authenticated"));
            }

            return userService.getUserByUsername(authentication.getName())
                    .map(user ->{
                        UserDTO userDTO = userService.convertToDTO(user);
                        return ResponseEntity.ok( new ApiResponse("Success", userDTO));
                    })
                    .orElseGet(() -> ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error", "User not found")));
        }
        catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", "Unexpected error: " + e.getMessage()));
        }
    }

    /*
     * POST Method
     *
     * Authentication endpoints are available to public guests for registration and login
     */

    /**
     * <h1>POST: Register</h1>
     * <h5>URL: api/v1/auth/register</h5>
     * <br>
     *
     * <li>Creates a new user account with the provided credentials</li>
     * <li>Validates username and email uniqueness</li>
     *
     * @param request {@link UserCreateRequest} containing user registration details
     * @return {@link ApiResponse} containing authentication token and user information
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserCreateRequest request, HttpServletResponse response) {
        try {
            // Register for user
            TokenForm token = authenticationService.register(request);
            if (token.getStatus().equals(Boolean.FALSE)) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("Failed", token));
            }
            // Get user after register
            User user = userService.getUserByUsername(request.getUsername()).orElse(null);;
            if (user == null) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Error", "Failed to retrieve user after registration"));
            }

            // Create refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
            token.setRefreshToken(refreshToken.getToken());

            // Use HttpOnly Cookie for access token
            Cookie accessCookie = new Cookie("accessToken", token.getToken());
            accessCookie.setHttpOnly(true);
            accessCookie.setSecure(false); // only use with https
            accessCookie.setPath("/");
            accessCookie.setMaxAge(15*60); // expired after 15 minutes
            response.addCookie(accessCookie);

            // Use HttpOnly Cookie for refreshToken
            Cookie refreshCookie = new Cookie("refreshToken", token.getRefreshToken());
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(false);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(30 * 24 * 60 * 60); // expired after 30 days
            response.addCookie(refreshCookie);

            return ResponseEntity.ok(new ApiResponse("Success", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", "Unexpected error: " + e.getMessage()));
        }
    }

    /**
     * <h1>POST: Login</h1>
     * <h5>URL: api/v1/auth/login</h5>
     * <br>
     *
     * <li>Authenticates a user with the provided credentials</li>
     * <li>Creates and returns a JWT token on successful authentication</li>
     *
     * @param request {@link LoginRequest} containing login credentials
     * @return {@link ApiResponse} containing authentication token and user information
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            TokenForm token = authenticationService.login(request);
            if (token.getStatus().equals(Boolean.FALSE)) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("Failed", token));
            }

            // Create refresh token
            User user = userService.getUserByUsername(request.getUsername()).orElse(null);;
            assert user != null;
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
            token.setRefreshToken(refreshToken.getToken());

            // Use HttpOnly Cookie for access token
            Cookie accessCookie = new Cookie("accessToken", token.getToken());
            accessCookie.setHttpOnly(true);
            accessCookie.setSecure(true); // only use with https
            accessCookie.setPath("/");
            accessCookie.setMaxAge(15*60); // expired after 15 minutes
            response.addCookie(accessCookie);

            // Use HttpOnly Cookie for refreshToken
            Cookie refreshCookie = new Cookie("refreshToken", token.getRefreshToken());
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(true);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(30 * 24 * 60 * 60); // expired after 30 days
            response.addCookie(refreshCookie);

            return ResponseEntity.ok(new ApiResponse("Success", token));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new ApiResponse("Error", e.getMessage()));
        }
    }

    /**
     * <h1>POST: Refresh Token</h1>
     * <h5>URL: api/v1/auth/refresh</h5>
     * <br>
     *
     * <li>Refreshes an expired access token using a valid refresh token</li>
     * <li>Issues a new access token via HttpOnly cookie</li>
     * <li>Maintains the same refresh token if it's still valid</li>
     *
     * @param request {@link RefreshTokenRequest} containing the refresh token
     * @param response {@link HttpServletResponse} for setting the cookie
     * @return {@link ApiResponse} containing new token information
     */
    // Dev Note: This should be in SecurityConfig.java ?
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody RefreshTokenRequest request, HttpServletResponse response) {
        try {
            RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
            User user = refreshToken.getUser();

            // Generate new access token
            TokenForm token = authenticationService.generateAccessToken(user, refreshToken);

            // Use HttpOnly Cookie for access token
            Cookie accessCookie = new Cookie("accessToken", token.getToken());
            accessCookie.setHttpOnly(true);
            accessCookie.setSecure(true); // only use with https
            accessCookie.setPath("/");
            accessCookie.setMaxAge(15*60); // expired after 15 minutes
            response.addCookie(accessCookie);

            return ResponseEntity.ok(new ApiResponse("Success", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }
}
