package com.example.project_socialmedia.infrastructure.Request.User;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
