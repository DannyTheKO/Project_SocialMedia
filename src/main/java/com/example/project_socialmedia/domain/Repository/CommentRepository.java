package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
