package com.project.social_media.controllers.Request.Authentication;

import lombok.Data;

@Data
public class TokenForm {
    private Boolean status;
    private String message;
    private String token;
}
