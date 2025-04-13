package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.CommentDTO;
import com.project.social_media.application.DTO.LikeDTO;
import com.project.social_media.application.DTO.MediaDTO;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.Service_Interface.ICommentService;
import com.project.social_media.application.Service_Interface.ILikeService;
import com.project.social_media.application.Service_Interface.IMediaService;
import com.project.social_media.controllers.Request.Comment.CommentCreateRequest;
import com.project.social_media.controllers.Request.Comment.CommentUpdateRequest;
import com.example.project_socialmedia.domain.Model.*;
import com.project.social_media.domain.Model.Comment;
import com.project.social_media.domain.Model.MediaAssociation;
import com.project.social_media.domain.Model.Post;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.CommentRepository;
import com.project.social_media.domain.Repository.MediaAssociationRepository;
import com.project.social_media.domain.Repository.PostRepository;
import com.project.social_media.domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final ModelMapper modelMapper;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MediaAssociationRepository mediaAssociationRepository;

    private final IMediaService mediaService;
    private final ILikeService likeService;

    private final String uploadDir = "gui/src/assets/uploads/posts";

    /**
     * Get Comment By ID
     *
     * @param id Long
     * @return Object {Comment}
     */
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("getCommentById: commentId not found"));
    }

    /**
     * Get All Comment
     *
     * @return List{Object}: {Comment}
     */
    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    /**
     * Get All User Comments By User ID
     *
     * @param userId Long
     * @return List{Object}: {Comment}
     */
    @Override
    public List<Comment> getAllCommentsByUserId(Long userId) {
        User existingUser = userRepository.findUserByUserId(userId);
        return existingUser.getComments();
    }

    /**
     * Get All User Comment From Post
     *
     * @param postId Long
     */
    @Override
    public List<Comment> getAllCommentsByPostId(Long postId) {
        Post getPost = postRepository.findPostByPostId(postId);
        return getPost.getComments();
    }

    /**
     * Add Comment
     *
     * @param request Object {CommentCreateRequest}
     */
    @Override
    @PreAuthorize("hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
    public Comment createComment(Long userId, Long postId, CommentCreateRequest request) {
        try {
            User existingUser = userRepository.findUserByUserId(userId);
            Post existingPost = postRepository.findPostByPostId(postId);

            Comment newComment = new Comment(
                    existingUser,
                    existingPost,
                    request.getContent(),
                    LocalDateTime.now(),    // Created At
                    LocalDateTime.now()     // Updated At
            );

            // Set CommentID first
            commentRepository.save(newComment);

            // Handle media
            List<MultipartFile> mediaFiles = request.getMediaFileRequest();
            if(mediaFiles != null) {
                mediaFiles.stream()
                        .filter(mediaFile -> !mediaFile.isEmpty())
                        .forEach(mediaFile -> mediaService.saveFile(
                                mediaFile,
                                uploadDir + "/" + postId + "/comments/" + newComment.getCommentId() + "/",
                                newComment.getCommentId(),
                                "Comment"
                        ));
            }
            return newComment;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Update Comment
     *
     * @param request Object {CommentUpdateRequest}
     */
    @Override
    public Comment updateComment(Long userId, Long postId, Long commentId, CommentUpdateRequest request) {
        Comment getComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFound("updateComment: commentId not found"));

        List<MediaAssociation> oldMediaFiles = mediaAssociationRepository.findByTargetIdAndTargetType(commentId, "Comment");
        List<MultipartFile> mediaFiles = request.getMediaFileRequest();
        if(mediaFiles != null) {
            oldMediaFiles.forEach(oldMediaFile -> {
                mediaService.removeFile(commentId, oldMediaFile.getTargetType(), oldMediaFile.getMedia().getFileType());
            });

            mediaFiles.stream()
                    .filter(mediaFile -> !mediaFile.isEmpty())
                    .forEach(mediaFile -> mediaService.saveFile(
                            mediaFile,
                            uploadDir + "/" + postId + "/comments/" + commentId + "/",
                            commentId,
                            "Comment"
                    ));
        }

        // Change
        getComment.setContent(request.getContent());
        getComment.setUpdatedAt(LocalDateTime.now());

        // Send to Database
        commentRepository.save(getComment);
        return getComment;
    }

    /**
     * Delete Comment By ID
     *
     * @param commentId Long
     */
    @Override
    public void deleteCommentById(Long commentId) {
        try {
            Comment getComment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new ResourceNotFound("deleteCommentById: commentId not found"));

            // Remove related media
            List<MediaAssociation> mediaAssociationList = mediaAssociationRepository.findByTargetIdAndTargetType(commentId, "Comment");
            mediaAssociationList.forEach(mediaAssociation -> {
                mediaService.removeFile(commentId, "Comment", mediaAssociation.getMedia().getFileType());
            });

            commentRepository.delete(getComment);
        } catch (ResourceNotFound e) {
            throw new RuntimeException(e);
        }
    }

    public CommentDTO convertToDTO(Comment comment) {
        CommentDTO mappedDTO = modelMapper.map(comment, CommentDTO.class);

        // Set PostId
        mappedDTO.setPostId(comment.getPost().getPostId());

        mappedDTO.setUsername(comment.getPost().getUser().getUsername());
        mappedDTO.setUserId(comment.getUser().getUserId());
        mappedDTO.setFirstName(comment.getUser().getFirstName());
        mappedDTO.setLastName(comment.getUser().getLastName());

        // Set Media
        List<MediaDTO> mediaDTOList = mediaService.getMediaDTOByTargetIdAndTargetType(comment.getCommentId(), "Comment");
        mappedDTO.setMedia(mediaDTOList);

        // Set Like
        List<LikeDTO> likeDTOList = likeService.convertToDTOList(comment.getLikes());
        mappedDTO.setLikes(likeDTOList);

        return mappedDTO;
    }

    public List<CommentDTO> convertToDTOList(List<Comment> commentList) {
        return commentList.stream().map(this::convertToDTO).toList();
    }
}
