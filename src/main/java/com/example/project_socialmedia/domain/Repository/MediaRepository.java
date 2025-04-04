package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {

}
