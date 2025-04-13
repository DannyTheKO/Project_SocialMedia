package com.project.social_media.application.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MediaDTO {
    private Long mediaId;
    private String filePath;
    private String fileName;
    private LocalDateTime uploadedDate;
}
