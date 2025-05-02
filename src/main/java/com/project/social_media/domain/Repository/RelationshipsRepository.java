package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.Relationships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationships, Long> {

    boolean existsByUser1UserIdAndUser2UserIdAndStatus(Long userId1, Long userId2, Relationships.RelationshipStatus status);

    @Query("SELECT r FROM Relationships r WHERE (r.user1.userId =:userId OR r.user2.userId = :userId) AND r.status = :status")
    List<Relationships> findByUserIdAndStatus(Long userId, Relationships.RelationshipStatus status);

    @Query("SELECT r FROM Relationships r WHERE " +
            "((r.user1.userId = :userId1 AND r.user2.userId = :userId2 AND r.status = :status) OR " +
            "(r.user1.userId = :userId2 AND r.user2.userId = :userId1 AND r.status = :status))")
    Optional<Relationships> findByUserIdsAndStatus(
            @Param("userId1") Long userId1,
            @Param("userId2") Long userId2,
            @Param("status") Relationships.RelationshipStatus status);
}
