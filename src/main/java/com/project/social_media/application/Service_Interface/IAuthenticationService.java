package com.project.social_media.application.Service_Interface;

import com.project.social_media.controllers.Request.Authentication.LoginRequest;
import com.project.social_media.controllers.Request.Authentication.TokenForm;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import org.springframework.security.core.Authentication;

public interface IAuthenticationService {

    TokenForm register(UserCreateRequest request) throws RuntimeException;

    TokenForm login(LoginRequest request) throws RuntimeException;

    void authenticationCheck(Authentication authentication);
}
