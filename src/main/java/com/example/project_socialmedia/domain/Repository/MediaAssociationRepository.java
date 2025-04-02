package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Modal.MediaAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaAssociationRepository extends JpaRepository<MediaAssociation, Long> {
    List<MediaAssociation> findByTargetIdAndTargetType(Long targetId, String targetType);
}
