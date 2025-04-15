package com.project.social_media.application.DTO;

import com.project.social_media.domain.Model.Relationships;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RelationshipsDTO {
    private Long relationshipId;
    private UserDTO user1;
    private UserDTO user2;
    private Relationships.RelationshipStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
