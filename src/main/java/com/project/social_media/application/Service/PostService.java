package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.*;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.IService.*;
import com.project.social_media.controllers.Request.Post.PostCreateRequest;
import com.project.social_media.controllers.Request.Post.PostUpdateRequest;
import com.project.social_media.domain.Model.*;
import com.project.social_media.domain.Repository.MediaAssociationRepository;
import com.project.social_media.domain.Repository.PostRepository;
import com.project.social_media.domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MediaAssociationRepository mediaAssociationRepository;

    private final IUserService userService;
    private final IMediaService mediaService;
    private final ICommentService commentService;
    private final ILikeService likeService;

    private final String uploadDir = "gui/src/Assets/uploads/posts/";


    /**
     * Get All Post
     *
     * @return List{Object} Post
     */
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllOrderByCreatedPostDesc();
    }

    /**
     * Get Post By ID
     *
     * @param postId Long
     * @return Object {Post}
     */
    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("getPostById: post not found"));
    }

    /**
     * Get All Post By User ID
     *
     * @param userId Long
     * @return List{Object} Post
     */
    @Override
    public List<Post> getAllPostsByUserId(Long userId) {
        return userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userId))
                .getPosts();
    }

    /**
     * Get Media By Post ID
     * <p> dev note: why is this here :'(
     *
     * @param postId Long
     * @return Object {Media}
     */
    public List<Media> getMediaByPostId(Long postId) {
        List<MediaAssociation> associations = mediaAssociationRepository.findByTargetIdAndTargetType(postId, "Post");
        return associations.stream().map(MediaAssociation::getMedia).toList();
    }

    /**
     * Create Post
     *
     * @param request Object {PostCreateRequest}
     * @param userId  Long
     * @return Object {Post}
     */
    @Override
    public Post createPost(PostCreateRequest request, Long userId) {
        try {
            // Check if User exist in the database
            User user = userRepository.findUserByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFound("createPost: userId not found"));

            Post newPost = new Post(
                    user,
                    new ArrayList<Comment>(),
                    new ArrayList<Like>(),
                    request.getContent(),
                    LocalDateTime.now(),        // CreatedPost
                    LocalDateTime.now()         // ModifiedPost
            );

            postRepository.save(newPost); // Save the post first to get the generated ID

            // Now handle media
            List<MultipartFile> mediaFiles = request.getFiles();
            if (mediaFiles != null) {
                mediaFiles.stream()
                        .filter(mediaFile -> !mediaFile.isEmpty())
                        .forEach(mediaFile -> mediaService.saveFile(
                                mediaFile,
                                uploadDir + newPost.getPostId() + "/",
                                newPost.getPostId(),
                                "Post"
                        ));
            }

            return newPost;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete Post
     *
     * @param postId Long
     */
    @Override
    public void deletePost(Long postId) {
        try {
            Post existingPost = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFound("deletePost: Post not found"));

            List<Comment> existingComments = existingPost.getComments();
            existingComments.forEach(comment -> {
                commentService.deleteCommentById(comment.getCommentId());
            });

            List<MediaAssociation> mediaAssociationList = mediaAssociationRepository.findByTargetIdAndTargetType(postId, "Post");
            mediaAssociationList.forEach(mediaAssociation -> {
                mediaService.removeFile(postId, "Post", mediaAssociation.getMedia().getFileType());
            });

            postRepository.delete(existingPost);
        } catch (ResourceNotFound e) {
            // Include original exception for better debugging
            throw new RuntimeException("Error deleting post: " + e.getMessage(), e);
        }
    }

    /**
     * Update Post
     *
     * @param request Object {PostUpdateRequest}
     * @param userId  Long
     * @param postId  Long
     * @return Object {Object}
     */
    @Override
    public Post updatePost(Long userId, Long postId, PostUpdateRequest request) {
        try {
            // 1. Retrieve the post using postId
            Post existingPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("updatePost: Post not found"));

            // 3. If authorized, proceed with the update logic
            existingPost.setContent(request.getContent());

            // Handle Media Updates
            List<MediaAssociation> oldMediaFiles = mediaAssociationRepository.findByTargetIdAndTargetType(postId, "Post");
            List<MultipartFile> MediaFiles = request.getMediaFileRequest();

            // Add new media files
            if (MediaFiles != null) {
                // Remove old file from local machine
                oldMediaFiles.forEach(oldMediaFile -> {
                    mediaService.removeFile(postId, oldMediaFile.getTargetType(), oldMediaFile.getMedia().getFileType());
                });

                // Add new file into local machine
                MediaFiles.stream()
                        .filter(mediaFile -> !mediaFile.isEmpty())
                        .forEach(mediaFile -> mediaService.saveFile(
                                mediaFile,
                                uploadDir + postId + "/",
                                postId,     // This is the targetId
                                "Post"      // This is the targetType
                        ));
            }

            postRepository.save(existingPost);
            return existingPost;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public PostDTO convertToDTO(Post post) {
        PostDTO mappedPostDTO = modelMapper.map(post, PostDTO.class);

        // Set UserDTO
        UserDTO userDTO = userService.convertToDTO(post.getUser());
        mappedPostDTO.setUser(userDTO);

        List<CommentDTO> commentDTOList = commentService.convertToDTOList(post.getComments());
        mappedPostDTO.setComments(commentDTOList);

        // Set Like
        List<LikeDTO> likeDTOList = likeService.convertToDTOList(post.getLikes());
        mappedPostDTO.setLikes(likeDTOList);

        // Set Media
        List<MediaDTO> mediaDTOList = mediaService.getMediaDTOByTargetIdAndTargetType(post.getPostId(), "Post");
        mappedPostDTO.setMedia(mediaDTOList);

        return mappedPostDTO;
    }

    public List<PostDTO> convertToListDTO(List<Post> postList) {
        return postList.stream().map(this::convertToDTO).toList();
    }
}
