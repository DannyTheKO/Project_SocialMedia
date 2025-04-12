package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.controllers.Request.User.UserCreateRequest;

public interface IAuthenticationService {

    String register(UserCreateRequest request) throws RuntimeException;

    String login(String username, String password) throws RuntimeException;
}
