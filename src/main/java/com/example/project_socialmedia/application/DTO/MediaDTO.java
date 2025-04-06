package com.example.project_socialmedia.application.DTO;

import com.example.project_socialmedia.domain.Model.Media;
import lombok.Data;

@Data
public class MediaDTO {
    private Long mediaId;
    private String url;
    private String fileName;
}
