package com.project.social_media.controllers.Request.FriendRequest;

import com.project.social_media.domain.Model.FriendRequest.FriendRequestStatus;
import lombok.Data;

@Data
public class FriendRequestUpdateRequest {
    private FriendRequestStatus status; // PENDING, ACCEPTED, REJECTED
}
