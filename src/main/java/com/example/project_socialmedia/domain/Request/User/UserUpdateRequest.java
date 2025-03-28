package com.example.project_socialmedia.domain.Request.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
