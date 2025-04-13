package com.project.social_media.application.Service_Interface;

import com.project.social_media.application.DTO.MediaDTO;
import com.project.social_media.domain.Model.Media;
import com.project.social_media.domain.Model.MediaAssociation;
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
