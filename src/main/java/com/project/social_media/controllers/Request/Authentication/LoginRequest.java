package com.project.social_media.controllers.Request.Authentication;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
