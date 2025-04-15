package com.project.social_media.controllers.Request.Relationships;

import com.project.social_media.domain.Model.Relationships.RelationshipStatus;
import lombok.Data;

// khi cập nhật trạng thái quan hệ (từ bạn bè sang chặn hoặc ngược lại)
@Data
public class RelationshipsUpdateRequest {
    private RelationshipStatus status; // FRIENDS, BLOCKED
}