package com.project.social_media.application.DTO;

import com.project.social_media.domain.Model.FriendRequest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendRequestDTO {
    private Long friendRequestId;
    private UserDTO fromUser;
    private UserDTO toUser;
    private FriendRequest.FriendRequestStatus status;
    private LocalDateTime createdAt;
}
