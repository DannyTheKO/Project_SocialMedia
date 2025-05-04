package com.project.social_media.application.IService;

import com.project.social_media.application.DTO.RelationshipsDTO;
import com.project.social_media.domain.Model.JPA.Relationships;
import com.project.social_media.controllers.ApiResponse.FriendshipCheck;
import org.springframework.data.domain.Page;

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
     * @param relationship List<Relationship>
     * @return List<RelationshipDTO>
     */

    void acceptFriendRequest(Long relationshipId);

    void rejectFriendRequest(Long relationshipId);

    void blockUser(Long userId1, Long userId2);

    FriendshipCheck checkFriendship(Long userId1, Long userId2);

    RelationshipsDTO convertToDTO(Relationships relationship);

    List<RelationshipsDTO> convertToListDTO(List<Relationships> relationships , Long currentUserId);

    Long findRelationshipId(Long userId1, Long userId2);

    Long findPendingRelationshipId(Long userId1, Long userId2);

    List<Relationships> getPendingRequests(Long userId);
}
