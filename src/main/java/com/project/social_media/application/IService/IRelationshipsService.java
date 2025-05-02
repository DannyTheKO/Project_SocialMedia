package com.project.social_media.application.IService;

import com.project.social_media.application.DTO.RelationshipsDTO;
import com.project.social_media.controllers.ApiResponse.FriendshipCheck;
import com.project.social_media.domain.Model.Relationships;

import java.util.List;

public interface IRelationshipsService {
    /**
     * Get friends by user ID (paginated)
     *
     * @param userId Long
     * @return Page<Relationship>
     */
    List<Relationships> getFriends(Long userId);

    /**
     * Get relationship by ID
     *
     * @param relationshipId Long
     * @return Relationship
     */
    Relationships getRelationshipById(Long relationshipId);

    /**
     * Create a new relationship
     *
     * @param userId1 Long
     * @param userId2 Long
     * @param status Relationship.RelationshipStatus
     * @return Relationship
     */
    Relationships createRelationship(Long userId1, Long userId2, Relationships.RelationshipStatus status);

    /**
     * Update relationship status
     *
     * @param relationshipId Long
     * @param status Relationship.RelationshipStatus
     * @return Relationship
     */
    Relationships updateRelationship(Long relationshipId, Relationships.RelationshipStatus status);

    /**
     * Delete relationship
     *
     * @param relationshipId Long
     */
    void deleteRelationship(Long relationshipId);

    /**
     * Convert Relationship to DTO
     *
     * @param relationship Relationship
     * @return RelationshipDTO
     */
    RelationshipsDTO convertToDTO(Relationships relationship, Long currentUserId);

    /**
     * Convert list of Relationship to DTO
     *
     * @param relationships List<Relationship>
     * @return List<RelationshipDTO>
     */

    RelationshipsDTO convertToDTO(Relationships relationship);

    List<RelationshipsDTO> convertToListDTO(List<Relationships> relationships , Long currentUserId);

    FriendshipCheck areFriends(Long userId1, Long userId2);
}
