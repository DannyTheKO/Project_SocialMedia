package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Service_Interface.IPostService;
import com.example.project_socialmedia.domain.Modal.Media;
import com.example.project_socialmedia.domain.Modal.Post;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.domain.Repository.PostRepository;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import com.example.project_socialmedia.infrastructure.Exception.ResourceNotFound;
import com.example.project_socialmedia.infrastructure.Request.Post.PostCreateRequest;
import com.example.project_socialmedia.infrastructure.Request.Post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * Identify the media type when pass in
     * @param url   URL of the file type
     * @return      return a string type
     */
    private String identifyMediaType(String url) {
        if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".png") || url.toLowerCase().endsWith(".jpeg")) {
            return "image";
        } else if (url.toLowerCase().endsWith(".mp4") || url.toLowerCase().endsWith(".mov") || url.toLowerCase().endsWith(".avi")) {
            return "video";
        } else {
            return "unknown"; // Or handle other types as needed
        }
    }


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
     * Get All Post By User ID
     * TODO: getPostsByUserId [Need Testing]
     *
     * @param userId Long
     * @return List{Object} Post
     */
    @Override
    public List<Post> getPostsByUserId(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("getPostsByUserId: User not found"));

        return existingUser.getPosts();
    }

    /**
     * Create Post
     * TODO: createPost [Need Testing]
     *
     * @param request Object {PostCreateRequest}
     * @param userId  Long
     * @return Object {Post}
     */
    @Override
    public Post createPost(PostCreateRequest request, Long userId) {
        // Check if User exist in the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("createPost: User not found"));

        Post newPost = new Post(
                user,
                request.getContent(),
                new ArrayList<>(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        List<Media> newMedia = request.getMediaUrls().stream()
                .map(url -> new Media(url, identifyMediaType(url), newPost)) // Associate with Post
                .toList();

        // Add the Media objects to the Post
        newPost.setMedia(newMedia);
        postRepository.save(newPost);
        return newPost;
    }

    /**
     * Delete Post
     * TODO: deletePost [Need Testing]
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
     * TODO: Find a way to edit, add or delete image and video
     *
     * @param request Object {PostUpdateRequest}
     * @param userId  Long
     * @param postId  Long
     * @return Object {Object}
     */
    @Override
    public Post updatePost(PostUpdateRequest request, Long userId, Long postId) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("updatePost: Post not found"));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("updatePost: User not found"));

        existingPost.setContent(request.getContent());

        //Handle Media Updates:
        List<Media> existingMedia = existingPost.getMedia();
        List<String> newMediaUrls = request.getMediaUrls();

        // Remove media that are no longer present in the request
        existingMedia.removeIf(media -> !newMediaUrls.contains(media.getUrl()));

        // Add new media URLs
        for (String newUrl : newMediaUrls) {
            if (existingMedia.stream().noneMatch(media -> media.getUrl().equals(newUrl))) {
                existingPost.getMedia().add(new Media(newUrl, identifyMediaType(newUrl), existingPost));
            }
        }

        postRepository.save(existingPost);
        return existingPost;
    }
}
