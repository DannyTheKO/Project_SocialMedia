package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostByPostId(Long postId);
}
