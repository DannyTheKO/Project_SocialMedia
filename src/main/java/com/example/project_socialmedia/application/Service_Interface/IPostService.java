package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.domain.Modal.Post;
import com.example.project_socialmedia.infrastructure.Request.Post.PostCreateRequest;
import com.example.project_socialmedia.infrastructure.Request.Post.PostUpdateRequest;

import java.io.IOException;
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
     * @param postId    Long
     * @return          Object {Post}
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
    Post createPost(PostCreateRequest request, Long userId) throws IOException;

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
    Post updatePost(PostUpdateRequest request, Long userId, Long postId) throws IOException;
}
