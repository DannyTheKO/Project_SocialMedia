package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.Comment;
import com.project.social_media.domain.Model.Like;
import com.project.social_media.domain.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findLikesByPost(Post post);

    List<Like> findLikesByComment(Comment comment);

}
