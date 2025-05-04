package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByPostId(Long postId);

    @Query("SELECT p FROM Post p ORDER BY p.createdPost DESC")
    List<Post> findAllOrderByCreatedPostDesc();
}
