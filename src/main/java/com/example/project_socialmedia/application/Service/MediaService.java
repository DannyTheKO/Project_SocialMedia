package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.IMediaService;
import com.example.project_socialmedia.domain.Modal.Media;
import com.example.project_socialmedia.domain.Repository.MediaRepository;
import com.example.project_socialmedia.domain.Repository.PostRepository;
import com.example.project_socialmedia.infrastructure.Config.Request.Media.CreateMediaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Media createMedia(CreateMediaRequest request) {
        Media newMedia = new Media();

        newMedia.setUrl(request.getUrl());
        newMedia.setFileType(request.getFileType());
        newMedia.setFileName(request.getFileName());
        newMedia.setPost(request.getPost());

        mediaRepository.save(newMedia);
        return newMedia;
    }

}
