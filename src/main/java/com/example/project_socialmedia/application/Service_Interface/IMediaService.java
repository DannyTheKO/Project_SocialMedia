package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.application.DTO.MediaDTO;
import com.example.project_socialmedia.domain.Model.Media;
import com.example.project_socialmedia.domain.Model.MediaAssociation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMediaService {
    List<Media> getAllMedia();

    List<MediaAssociation> getAllMediaAssociation();

    Media getMediaById(Long mediaId);

    MediaAssociation getMediaAssociationById(Long mediaAssociationId);

    List<MediaDTO> getMediaDTOByTargetIdAndTargetType(Long targetId, String targetType);

    String identifyMediaType(String filePath);

    Media saveFile(MultipartFile file, String uploadDir, Long targetId, String targetType);

    void removeFile(Long targetId, String targetType, String fileType);
}
