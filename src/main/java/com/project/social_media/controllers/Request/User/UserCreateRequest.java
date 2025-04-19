package com.project.social_media.controllers.Request.User;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String birthday;
}
