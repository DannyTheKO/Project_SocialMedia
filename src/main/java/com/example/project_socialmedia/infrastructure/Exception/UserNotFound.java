package com.example.project_socialmedia.infrastructure.Exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
}
