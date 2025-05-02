package com.project.social_media.application.IService;

import com.project.social_media.controllers.Request.Authentication.LoginRequest;
import com.project.social_media.controllers.Request.Authentication.TokenForm;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.domain.Model.JPA.RefreshToken;
import com.project.social_media.domain.Model.JPA.User;
import org.springframework.security.core.Authentication;

public interface IAuthenticationService {

    TokenForm register(UserCreateRequest request) throws RuntimeException;

    TokenForm login(LoginRequest request) throws RuntimeException;

    TokenForm generateAccessToken(User user, RefreshToken refreshToken);

    void checkValidationAuth(Authentication authentication);
}
