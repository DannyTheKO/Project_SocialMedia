package com.project.social_media.controllers.Request.Relationships;

import com.project.social_media.domain.Model.JPA.Relationships.RelationshipStatus;
import lombok.Data;

// khi tạo quan hệ (thêm bạn bè hoặc chặn).
@Data
public class RelationshipsCreateRequest {
    private Long receiverId;
    private RelationshipStatus status;
}