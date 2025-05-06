package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.Post;
import com.project.social_media.domain.Model.JPA.SharedPost;
import com.project.social_media.domain.Model.JPA.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharedPostRepository extends JpaRepository<SharedPost, Long> {
    List<SharedPost> findByUser(User user);

    @Query("SELECT COUNT(sp) FROM SharedPost sp WHERE sp.originalPost = :originalPost")
    long countSharesByOriginalPost(@Param("originalPost") Post originalPost);
}