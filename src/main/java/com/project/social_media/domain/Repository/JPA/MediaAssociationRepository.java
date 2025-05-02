package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.MediaAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaAssociationRepository extends JpaRepository<MediaAssociation, Long> {
    List<MediaAssociation> findByTargetIdAndTargetType(Long targetId, String targetType);
}
