package com.project.social_media.application.Service;


import com.project.social_media.application.Exception.AuthenticationException;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IRefreshTokenService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.Request.Authentication.LoginRequest;
import com.project.social_media.controllers.Request.Authentication.TokenForm;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.domain.Model.RefreshToken;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.UserRepository;
import com.project.social_media.infrastructure.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final IRefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @Override
    public TokenForm register(UserCreateRequest request) throws RuntimeException {
        // Validation
        TokenForm tokenForm = new TokenForm();
        try {
            // Check exist username
            if (userService.getUserByUsername(request.getUsername()).isPresent()) {
                tokenForm.setStatus(Boolean.FALSE);
                tokenForm.setMessage("Username already exists");
                tokenForm.setToken(null);
                return tokenForm;
            }

            // Create
            User newUser = new User();
            newUser.setUsername(request.getUsername());
            newUser.setPassword(request.getPassword());
            newUser.setFirstName(request.getFirstname());
            newUser.setLastName(request.getLastname());
            newUser.setEmail(request.getEmail());
            newUser.setUserRole(User.userRole.USER); // Default Role
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setLastLogin(LocalDateTime.now());

            if (request.getBirthday() != null && !request.getBirthday().isEmpty()) {
                try {
                    LocalDate birthDate = LocalDate.parse(request.getBirthday());
                    // Convert LocalDate to LocalDateTime (default 00:00:00)
                    LocalDateTime birthDayTime = birthDate.atStartOfDay();
                    newUser.setBirthDay(birthDayTime);
                } catch (DateTimeParseException e) {
                    tokenForm.setStatus(Boolean.FALSE);
                    tokenForm.setMessage("Invalid birthday format. Expected format: YYYY-MM-DD");
                    tokenForm.setToken(null);
                    return tokenForm;
                }
            }

            userRepository.save(newUser);

            String userRole = newUser.getUserRole().toString();
            tokenForm.setStatus(Boolean.TRUE);
            tokenForm.setMessage("Successfully Registered");
            tokenForm.setToken(JwtUtil.generateToken(request.getUsername(), userRole));
            return tokenForm;
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            tokenForm.setStatus(Boolean.FALSE);
            tokenForm.setMessage("Registration failed: " + e.getMessage());
            tokenForm.setToken(null);
            return tokenForm;
        }
    }

    @Override
    public TokenForm login(LoginRequest request) throws RuntimeException {
        TokenForm tokenForm = new TokenForm();

        try {
            // Get user by username
            User existingUser = userService.getUserByUsername(request.getUsername())
                    .orElse(null); // Return null if username not found !

            // Check existing user and valid password
            if (existingUser == null || !existingUser.getPassword().equals(request.getPassword())) {
                tokenForm.setStatus(Boolean.FALSE);
                tokenForm.setMessage("Invalid Credentials");
                tokenForm.setToken(null);
                return tokenForm;
            }

            // Update last login
            String userRole = existingUser.getUserRole().toString();
            existingUser.updateUserLastLogin(LocalDateTime.now());
            userRepository.save(existingUser);

            // Generate access token
            String accessToken = JwtUtil.generateToken(request.getUsername(), userRole);

            // Generate refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(existingUser.getUserId());

            // Set tokenForm
            tokenForm.setStatus(Boolean.TRUE);
            tokenForm.setMessage("Successfully Logged In");
            tokenForm.setToken(accessToken);
            tokenForm.setRefreshToken(refreshToken.getToken());

            return tokenForm;
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
            tokenForm.setStatus(Boolean.FALSE);
            tokenForm.setMessage("Login failed: " + e.getMessage());
            tokenForm.setToken(null);
            tokenForm.setRefreshToken(null);
            return tokenForm;
        }
    }

    @Override
    public TokenForm generateAccessToken(User user, RefreshToken refreshToken){
        String userRole = user.getUserRole().toString();

        TokenForm tokenForm = new TokenForm();
        tokenForm.setStatus(Boolean.TRUE);
        tokenForm.setMessage("Token refreshed successfully");
        tokenForm.setToken(JwtUtil.generateToken(user.getUsername(), userRole)); // access token
        tokenForm.setRefreshToken(refreshToken.getToken());

        return tokenForm;
    }

    // Response to Controller !!!
    public void authenticationCheck(Authentication authentication) {
        if (authentication == null) {
            throw new AuthenticationException("Unable to Authenticate");
        }

        // If token is sent but invalid case
        userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Username not found"));
    }
}
