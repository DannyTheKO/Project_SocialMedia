package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.Relationships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationships, Long> {

    // Kiểm tra quan hệ bạn bè/chặn.
    boolean existsByUser1UserIdAndUser2UserIdAndStatus(Long userId1, Long userId2, Relationships.RelationshipStatus status);

    // Lấy danh sách bạn bè người dùng
    @Query("SELECT r FROM Relationships r WHERE (r.user1.userId =:userId OR r.user2.userId = :userId) AND r.status = :status")
    List<Relationships> findByUserIdAndStatus(Long userId, Relationships.RelationshipStatus status);
}
