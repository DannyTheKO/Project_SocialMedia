package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.IMediaService;
import com.example.project_socialmedia.domain.Modal.Media;
import com.example.project_socialmedia.domain.Modal.MediaAssociation;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.domain.Repository.MediaRepository;
import com.example.project_socialmedia.domain.Repository.PostRepository;
import com.example.project_socialmedia.infrastructure.Request.Media.CreateMediaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.project_socialmedia.domain.Modal.Media.fileType.*;

@Service
@RequiredArgsConstructor
public class MediaService implements IMediaService {
    private final MediaRepository mediaRepository;
    private final PostRepository postRepository;

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(Long mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFound("getMediaById: mediaId not found"));
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
            // Or handle other types as needed
            return GIF;
        } else {
            return UNKNOWN;
        }
    }

    /**
     * Save File Function
     *
     * @param file Object {MultipartFile}
     * @param uploadDir String
     * @param filePrefix String
     * @return String
     * @throws IOException  If something wrong
     */
    public Media saveFile(MultipartFile file, String uploadDir, String filePrefix, Long targetId, String targetType) throws IOException {
        String fileName = filePrefix + "_"
                + file.getOriginalFilename() + "_"
                + UUID.randomUUID();

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
                fileType
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

    }
}
