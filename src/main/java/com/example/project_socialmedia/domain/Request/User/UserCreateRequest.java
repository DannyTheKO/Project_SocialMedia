package com.example.project_socialmedia.domain.Request.User;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
