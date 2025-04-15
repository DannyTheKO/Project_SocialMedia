package com.project.social_media.application.Service_Interface;

import com.project.social_media.application.DTO.FriendRequestDTO;
import com.project.social_media.domain.Model.FriendRequest;

import java.util.List;

public interface IFriendRequestService {
    /**
     * Get received friend requests by user ID
     *
     * @param userId Long
     * @return List<FriendRequest>
     */
    List<FriendRequest> getReceivedFriendRequests(Long userId);

    /**
     * Get friend request by ID
     *
     * @param friendRequestId Long
     * @return FriendRequest
     */
    FriendRequest getFriendRequestById(Long friendRequestId);

   /**
    * Create a new friend request
    *
        @param fromUserId Long
        @param toUserId Long
        @return FriendRequest
   */
   FriendRequest createFriendRequest(Long fromUserId, Long toUserId);

    /**
     * Update friend request status
     *
     * @param friendRequestId Long
     * @param status FriendRequest.FriendRequestStatus
     * @return FriendRequest
     */
    FriendRequest updateFriendRequest(Long friendRequestId, FriendRequest.FriendRequestStatus status);

    /**
     * Delete friend request
     *
     * @param friendRequestId Long
     */
    void deleteFriendRequest(Long friendRequestId);

    /**
     * Convert FriendRequest to DTO
     *
     * @param friendRequest FriendRequest
     * @return FriendRequestDTO
     */
    FriendRequestDTO convertToDTO(FriendRequest friendRequest);

    /**
     * Convert list of FriendRequest to DTO
     *
     * @param friendRequests List<FriendRequest>
     * @return List<FriendRequestDTO>
     */
    List<FriendRequestDTO> convertToListDTO(List<FriendRequest> friendRequests);
}
