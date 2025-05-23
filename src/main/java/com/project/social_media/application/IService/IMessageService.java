package com.project.social_media.application.IService;

import com.project.social_media.domain.Model.MongoDB.Message;

import java.util.List;

public interface IMessageService {

    /**
     * Get all message between 2 users (userId1 and userId2)
     *
     * @param userId1 Long
     * @param userId2 Long
     */

    List<Message> getMessagesBetweenUsers(Long userId1, Long userId2);
}
