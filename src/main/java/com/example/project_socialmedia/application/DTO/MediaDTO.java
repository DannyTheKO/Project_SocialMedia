package com.example.project_socialmedia.application.DTO;

import com.example.project_socialmedia.domain.Model.Media;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MediaDTO {
    private Long mediaId;
    private String filePath;
    private String fileName;
    private LocalDateTime uploadedDate;
}
