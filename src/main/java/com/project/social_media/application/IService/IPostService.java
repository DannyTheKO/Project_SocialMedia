package com.project.social_media.application.IService;

import com.project.social_media.application.DTO.PostDTO;
import com.project.social_media.controllers.Request.Post.PostCreateRequest;
import com.project.social_media.controllers.Request.Post.PostUpdateRequest;
import com.project.social_media.domain.Model.JPA.Post;

import java.util.List;

public interface IPostService {
    /**
     * Get All Post
     *
     * @return List{Object} Post
     */
    List<Post> getAllPosts();

    /**
     * Get Post By ID
     *
     * @param postId Long
     * @return Object {Post}
     */
    Post getPostById(Long postId);

    /**
     * Get Post By User ID
     *
     * @param userId Long
     * @return List{Object} Post
     */
    List<Post> getAllPostsByUserId(Long userId);

    /**
     * Create Post
     *
     * @param request Object {PostCreateRequest}
     * @param userId  Long
     * @return Object {Post}
     */
    Post createPost(PostCreateRequest request, Long userId);

    /**
     * Delete Post
     *
     * @param postId Long
     */
    void deletePost(Long postId);

    /**
     * Update Post
     *
     * @param request Object {PostUpdateRequest}
     * @param userId  Long
     * @param postId  Long
     * @return Object {Object}
     */
    Post updatePost(Long userId, Long postId, PostUpdateRequest request);

    PostDTO convertToDTO(Post post);

    List<PostDTO> convertToListDTO(List<Post> postList);
}
