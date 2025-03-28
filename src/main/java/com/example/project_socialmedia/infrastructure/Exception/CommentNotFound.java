package com.example.project_socialmedia.infrastructure.Exception;

public class CommentNotFound extends RuntimeException {
    public CommentNotFound(String message) {
        super(message);
    }
}
