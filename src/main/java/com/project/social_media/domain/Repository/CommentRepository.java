package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByCommentId(Long commentId);
}
