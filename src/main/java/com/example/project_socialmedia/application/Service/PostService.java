package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.DTO.PostDTO;
import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.IPostService;
import com.example.project_socialmedia.domain.Model.Media;
import com.example.project_socialmedia.domain.Model.MediaAssociation;
import com.example.project_socialmedia.domain.Model.Post;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.MediaAssociationRepository;
import com.example.project_socialmedia.domain.Repository.PostRepository;
import com.example.project_socialmedia.controllers.Request.Post.PostCreateRequest;
import com.example.project_socialmedia.controllers.Request.Post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;
    private final MediaAssociationRepository mediaAssociationRepository;

    private final UserService userService;
    private final MediaService mediaService;


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
        User existingUser = userService.getUserById(userId);
        return existingUser.getPosts();
    }

    /**
     * Get Media By Post ID
     * @param postId    Long
     * @return          Object {Media}
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
                    request.getContent(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            postRepository.save(newPost); // Save the post first to get the generated ID

            // Now handle media
            List<MultipartFile> mediaFiles = request.getMedia();
            if (mediaFiles != null) {
                for (MultipartFile mediaFile : mediaFiles) {
                    if (!mediaFile.isEmpty()) {
                        mediaService.saveFile(
                                mediaFile,
                                "src/main/resources/uploads/posts/" + newPost.getPostId() + "/", // Use newPost.getPostId()
                                newPost.getPostId() + "_",
                                newPost.getPostId(),
                                "Post"
                        );
                    }
                }
            }

            return newPost;
        } catch (IOException e) {
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
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("deletePost: Post not found"));

        postRepository.delete(existingPost);
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
            Post existingPost = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFound("updatePost: Post not found"));

            // 2. Verify that the userId from the request matches the post's owner
            // TODO: Add Authorization
            User existingUser = userService.getUserById(userId);

            // 3. If authorized, proceed with the update logic
            existingPost.setContent(request.getContent());

            //Handle Media Updates:
            List<Media> existingMedia = getMediaByPostId(postId);
            List<MultipartFile> newMediaFiles = request.getMedia();

            // Add new media files
            if (newMediaFiles != null) {
                for (MultipartFile mediaFile : newMediaFiles) {
                    if (!mediaFile.isEmpty()) {
                        Media newMedia = mediaService.saveFile(
                                mediaFile,
                                "src/main/resources/uploads/posts/" + postId + "/",
                                postId + "_",
                                postId,     // This is the targetId
                                "Post"      // This is the targetType
                        );
                    }
                }
            }

            postRepository.save(existingPost);
            return existingPost;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public PostDTO convertToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    public List<PostDTO> convertToListDTO(List<Post> postList) {
        return postList.stream().map(this::convertToDTO).toList();
    }
}
