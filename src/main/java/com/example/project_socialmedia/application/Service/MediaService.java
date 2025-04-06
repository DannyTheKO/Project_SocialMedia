package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.DTO.MediaDTO;
import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.IMediaService;
import com.example.project_socialmedia.domain.Model.Media;
import com.example.project_socialmedia.domain.Model.MediaAssociation;
import com.example.project_socialmedia.domain.Repository.MediaAssociationRepository;
import com.example.project_socialmedia.domain.Repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.project_socialmedia.domain.Model.Media.fileType.*;

@Service
@RequiredArgsConstructor
public class MediaService implements IMediaService {
    private final MediaRepository mediaRepository;
    private final MediaAssociationRepository mediaAssociationRepository;

    private final ModelMapper modelMapper;

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public List<MediaAssociation> getAllMediaAssociation() {
        return mediaAssociationRepository.findAll();
    }

    public Media getMediaById(Long mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFound("getMediaById: mediaId not found"));
    }

    public MediaAssociation getMediaAssociationById(Long mediaAssociationId) {
        return mediaAssociationRepository.findById(mediaAssociationId)
                .orElseThrow(() -> new ResourceNotFound("getMediaAssociationById: mediaAssociationId not found"));
    }

    public List<MediaDTO> getMediaDTOByTargetIdAndTargetType(Long targetId, String targetType) {
        List<MediaAssociation> associations = mediaAssociationRepository.findByTargetIdAndTargetType(targetId, targetType);

        return associations.stream()
                .map(association -> modelMapper.map(association.getMedia(), MediaDTO.class))
                .toList();
    }


    /**
     * Identify the media type when pass in
     *
     * @param url URL of the file type
     * @return return a string type
     */
    private Enum<Media.fileType> identifyMediaType(String url) {
        if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".png") || url.toLowerCase().endsWith(".jpeg")) {
            return IMAGE;
        } else if (url.toLowerCase().endsWith(".mp4") || url.toLowerCase().endsWith(".mov") || url.toLowerCase().endsWith(".avi")) {
            return VIDEO;
        } else if (url.toLowerCase().endsWith(".gif")) {
            return GIF;
        } else {
            // Or handle other types as needed
            return UNKNOWN;
        }
    }

    /**
     * Save File Function
     *
     * @param file      Object {MultipartFile}
     * @param uploadDir String
     * @param targetId  Long
     * @return String
     */
    public Media saveFile(MultipartFile file, String uploadDir, Long targetId, String targetType) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Enum<Media.fileType> fileType = identifyMediaType(Objects.requireNonNull(file.getOriginalFilename()));

            // Create the full path to the file
            Path filePath = Paths.get(uploadDir, fileName);

            // Create the directory if it doesn't exist
            Files.createDirectories(filePath.getParent());

            // Save the file to the specified path
            Files.copy(file.getInputStream(), filePath);

            Media newMedia = new Media(
                    filePath.toString(),
                    fileName,
                    fileType,
                    LocalDateTime.now()
            );

            // Persist the Media object first
            newMedia = mediaRepository.save(newMedia);

            // Create and persist the MediaAssociation
            MediaAssociation association = new MediaAssociation(
                    null, // Auto-generated ID
                    newMedia,
                    targetId,
                    targetType
            );

            newMedia.getAssociations().add(association);
            mediaRepository.save(newMedia);

            return newMedia;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
