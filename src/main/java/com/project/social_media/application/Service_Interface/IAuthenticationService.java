package com.project.social_media.application.Service_Interface;

import com.project.social_media.controllers.Request.User.UserCreateRequest;

public interface IAuthenticationService {

    String register(UserCreateRequest request) throws RuntimeException;

    String login(String username, String password) throws RuntimeException;
}
