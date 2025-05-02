package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    // Kiểm tra yêu cầu kết bạn tồn tại.
    boolean existsByFromUserUserIdAndToUserUserIdAndStatus(Long fromUserId, Long toUserId, FriendRequest.FriendRequestStatus status);

    // Lấy danh sách yêu cầu kết bạn đến một người dùng (PENDING)
    Page<FriendRequest> findByToUserUserIdAndStatus(Long toUserId, FriendRequest.FriendRequestStatus status, Pageable pageable);
}
