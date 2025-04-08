package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.DTO.MediaDTO;
import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.IMediaService;
import com.example.project_socialmedia.domain.Model.Media;
import com.example.project_socialmedia.domain.Model.MediaAssociation;
import com.example.project_socialmedia.domain.Repository.MediaAssociationRepository;
import com.example.project_socialmedia.domain.Repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.NullServiceException;
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
     * @param filePath URL of the file type
     * @return return a string type
     */
    public String identifyMediaType(String filePath) {
        if (filePath.toLowerCase().endsWith(".jpg") ||
                filePath.toLowerCase().endsWith(".png") ||
                filePath.toLowerCase().endsWith(".jpeg")) {
            return "Image";

        } else if (filePath.toLowerCase().endsWith(".mp4") ||
                filePath.toLowerCase().endsWith(".mov") ||
                filePath.toLowerCase().endsWith(".avi")) {
            return "Video";

        } else if (filePath.toLowerCase().endsWith(".gif")) {
            return "GIF";
        } else {
            // Or handle other types as needed
            return "Unknown";
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
            String originalFilename = file.getOriginalFilename();
            String fileExtension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID() + "." + fileExtension;

            String fileType = identifyMediaType(Objects.requireNonNull(file.getOriginalFilename()));

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

    public void removeFile(Long targetId, String targetType, String fileType) {
        // Find the associated media based on targetId, targetType, and fileType
        MediaAssociation association = mediaAssociationRepository.findByTargetIdAndTargetType(targetId, targetType)
                .stream()
                .filter(a -> a.getMedia().getFileType().equalsIgnoreCase(fileType))
                .findAny()
                .orElse(null); // Return null if no association is found

        // If an association exists, proceed with deletion
        if (association != null) {
            Media mediaToRemove = association.getMedia();
            String filePathToRemove = mediaToRemove.getFilePath();

            try {
                // Delete the file from the system
                Path filePath = Paths.get(filePathToRemove);
                Files.deleteIfExists(filePath);

                // Remove the association from the database
                mediaAssociationRepository.delete(association);

                // Remove the media from the database
                mediaRepository.delete(mediaToRemove);
            } catch (IOException e) {
                // Handle the exception, maybe log it
                throw new RuntimeException("Error deleting file: " + e.getMessage());
            }
        }
    }
}
