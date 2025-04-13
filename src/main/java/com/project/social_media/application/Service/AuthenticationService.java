package com.project.social_media.application.Service;


import com.project.social_media.application.Exception.AuthenticationException;
import com.project.social_media.application.Service_Interface.IAuthenticationService;
import com.project.social_media.application.Service_Interface.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Authentication.LoginRequest;
import com.project.social_media.controllers.Request.Authentication.TokenForm;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.UserRepository;
import com.project.social_media.infrastructure.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final UserRepository userRepository;

    @Override
    public TokenForm register(UserCreateRequest request) throws RuntimeException {
        // Validation
        User checkUsername = userService.getUserByUsername(request.getUsername());
        TokenForm tokenForm = new TokenForm();

        if (checkUsername != null) {
            tokenForm.setStatus(Boolean.FALSE);
            tokenForm.setMessage("Username Already Exists");
            tokenForm.setToken(null);

            return tokenForm;
        }

        User checkEmail = userService.getUserByEmail(request.getEmail());
        if (checkEmail != null) {
            tokenForm.setStatus(Boolean.FALSE);
            tokenForm.setMessage("Email Already Exists");
            tokenForm.setToken(null);

            return tokenForm;
        }

        // Create
        User createUser = userService.createUser(request);
        String username = createUser.getUsername();
        String role = createUser.getUserRole().toString();

        tokenForm.setStatus(Boolean.TRUE);
        tokenForm.setMessage("Successfully Created User");
        tokenForm.setToken(JwtUtil.generateToken(username, role));;
        return tokenForm;
    }

    @Override
    public TokenForm login(LoginRequest request) throws RuntimeException {
        User existingUser = userService.getUserByUsername(request.getUsername());
        TokenForm tokenForm = new TokenForm();

        if (existingUser == null || !existingUser.getPassword().equals(request.getPassword())) {
            tokenForm.setStatus(Boolean.FALSE);
            tokenForm.setMessage("Invalid Credentials");
            tokenForm.setToken(null);

            return tokenForm;
        }

        String userRole = existingUser.getUserRole().toString();
        existingUser.updateUserLastLogin(LocalDateTime.now()); // Update Last Login Time
        userRepository.save(existingUser);

        tokenForm.setStatus(Boolean.TRUE);
        tokenForm.setMessage("Successfully Logged In");
        tokenForm.setToken(JwtUtil.generateToken(request.getUsername(), userRole));

        return tokenForm;
    }

    // Response to Controller !!!
    public void authenticationCheck(Authentication authentication) {
        if (authentication == null) {
            throw new AuthenticationException("Unable to Authenticate");
        }

        // If token is sent but invalid case
        User user = userService.getUserByUsername(authentication.getName());
        if(user == null) {
            throw new AuthenticationException("Unable to Authenticate");
        }
    }
}
