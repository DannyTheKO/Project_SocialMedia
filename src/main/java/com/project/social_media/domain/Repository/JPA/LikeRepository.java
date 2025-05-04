package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.Comment;
import com.project.social_media.domain.Model.JPA.Like;
import com.project.social_media.domain.Model.JPA.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findLikesByPost(Post post);

    List<Like> findLikesByComment(Comment comment);

}
