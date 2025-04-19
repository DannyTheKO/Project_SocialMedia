package com.project.social_media.controllers.ApiResponse;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse {
    private List<?> content;
    private int totalPages;
    private long totalElements;

    public PageResponse(List<?> content, int totalPages, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
