package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.Relationships;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationships, Long> {

    boolean existsByUser1UserIdAndUser2UserIdAndStatus(Long userId1, Long userId2, Relationships.RelationshipStatus status);

    // find relationship by two way
    @Query("SELECT r FROM Relationships r WHERE " +
            "((r.user1.userId = :userId1 AND r.user2.userId = :userId2) OR " +
            "(r.user1.userId = :userId2 AND r.user2.userId = :userId1))")
    Optional<Relationships> findByUserIds(
            @Param("userId1") Long userId1,
            @Param("userId2") Long userId2);

    // find friend request PENDING from userId 1 to userId 2
    @Query("SELECT r FROM Relationships r WHERE " +
            "r.user1.userId = :userId1 AND r.user2.userId = :userId2 AND r.status = :status")
    Optional<Relationships> findPendingRequest(
            @Param("userId1") Long userId1,
            @Param("userId2") Long userId2,
            @Param("status") Relationships.RelationshipStatus status);

    // find all friend list or friend request of user
    @Query("SELECT r FROM Relationships r WHERE " +
            "(r.user1.userId = :userId OR r.user2.userId = :userId) AND r.status = :status")
    List<Relationships> findByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") Relationships.RelationshipStatus status);

    @Query("SELECT r FROM Relationships r WHERE r.user2.userId = :userId AND r.status = 'PENDING'")
    Page<Relationships> findPendingRequestsByUserId(@Param("userId") Long userId, Pageable pageable);
}
