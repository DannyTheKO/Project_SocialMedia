package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Model.Comment;
import com.example.project_socialmedia.domain.Model.Like;
import com.example.project_socialmedia.domain.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findLikesByPost(Post post);

    List<Like> findLikesByComment(Comment comment);
}
