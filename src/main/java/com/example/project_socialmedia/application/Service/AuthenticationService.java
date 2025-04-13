package com.example.project_socialmedia.application.Service;


import com.example.project_socialmedia.application.Service_Interface.IAuthenticationService;
import com.example.project_socialmedia.application.Service_Interface.IUserService;
import com.example.project_socialmedia.controllers.Request.User.UserCreateRequest;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import com.example.project_socialmedia.infrastructure.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final UserRepository userRepository;

    // TODO: Add Authentication
    // NOTE: Logout will clear the token on client-side

    public String register(UserCreateRequest request) throws RuntimeException {
        // Validation
        User checkUsername = userService.getUserByUsername(request.getUsername());
        User checkEmail = userService.getUserByEmail(request.getEmail());

        if (checkUsername != null && checkEmail != null) {
            User createUser = userService.createUser(request);

            String username = createUser.getUsername();
            String role = createUser.getUserRole().toString();
            return JwtUtil.generateToken(username, role);
        }

        return null;
    }

    public String login(String username, String password) throws RuntimeException {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser == null || !existingUser.getPassword().equals(password)) {
            return null;
        }

        String userRole = existingUser.getUserRole().toString();
        existingUser.updateUserLastLogin(LocalDateTime.now()); // Update Last Login Time
        userRepository.save(existingUser);
        return JwtUtil.generateToken(username, userRole);
    }
}
