package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.DTO.MediaDTO;
import com.example.project_socialmedia.application.DTO.PostDTO;
import com.example.project_socialmedia.application.DTO.UserDTO;
import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.IPostService;
import com.example.project_socialmedia.controllers.Request.Post.PostCreateRequest;
import com.example.project_socialmedia.controllers.Request.Post.PostUpdateRequest;
import com.example.project_socialmedia.domain.Model.*;
import com.example.project_socialmedia.domain.Repository.MediaAssociationRepository;
import com.example.project_socialmedia.domain.Repository.PostRepository;
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

    private final PostRepository postRepository;
    private final MediaAssociationRepository mediaAssociationRepository;

    private final UserService userService;
    private final MediaService mediaService;

    private final String uploadDir = "gui/src/asset/uploads/posts/";


    /**
     * Get All Post
     *
     * @return List{Object} Post
     */
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * Get Post By ID
     *
     * @param postId Long
     * @return Object {Post}
     */
    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("getPostById: post not found"));
    }

    /**
     * Get All Post By User ID
     *
     * @param userId Long
     * @return List{Object} Post
     */
    @Override
    public List<Post> getAllPostsByUserId(Long userId) {
        User existingUser = userService.getUserById(userId);
        return existingUser.getPosts();
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
            User user = userService.getUserById(userId);

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
            List<MultipartFile> mediaFiles = request.getMedia();
            if (mediaFiles != null) {
                for (MultipartFile mediaFile : mediaFiles) {
                    if (!mediaFile.isEmpty()) {
                        mediaService.saveFile(
                                mediaFile,
                                uploadDir + newPost.getPostId() + "/",
                                newPost.getPostId(),
                                "Post"
                        );
                    }
                }
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
            Post existingPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("deletePost: Post not found"));

            List<MediaAssociation> mediaAssociationList = mediaAssociationRepository.findByTargetIdAndTargetType(postId, "Post");
            mediaAssociationList.forEach(mediaAssociation -> {
                mediaService.removeFile(postId, "Post", mediaAssociation.getMedia().getFileType());
            });

            postRepository.delete(existingPost);
        } catch (Exception e) {
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

            // TODO 2. Verify that the userId from the request matches the post's owner (Authentication)
            // User existingUser = userService.getUserById(userId);

            // 3. If authorized, proceed with the update logic
            existingPost.setContent(request.getContent());

            // Handle Media Updates
            List<MediaAssociation> oldMedia = mediaAssociationRepository.findByTargetIdAndTargetType(postId, "Post");
            List<MultipartFile> newMediaFiles = request.getMedia();

            // Add new media files
            if (newMediaFiles != null) {
                // Remove old file from local machine
                oldMedia.forEach(mediaAssociation -> {
                    mediaService.removeFile(postId, "Post", mediaAssociation.getMedia().getFileType());
                });

                // Add new file into local machine
                for (MultipartFile mediaFile : newMediaFiles) {
                    if (!mediaFile.isEmpty()) {
                        Media newMedia = mediaService.saveFile(mediaFile, uploadDir + postId + "/", postId,     // This is the targetId
                                "Post"      // This is the targetType
                        );
                    }
                }
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

        // TODO: Set Comment
        // TODO: Set Like

        // Set Media
        List<MediaDTO> mediaDTOList = mediaService.getMediaDTOByTargetIdAndTargetType(post.getPostId(), "Post");
        mappedPostDTO.setMedia(mediaDTOList);

        return mappedPostDTO;
    }

    public List<PostDTO> convertToListDTO(List<Post> postList) {
        return postList.stream().map(this::convertToDTO).toList();
    }
}
