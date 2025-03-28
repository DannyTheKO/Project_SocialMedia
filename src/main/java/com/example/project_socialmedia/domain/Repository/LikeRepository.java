package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Modal.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

}
