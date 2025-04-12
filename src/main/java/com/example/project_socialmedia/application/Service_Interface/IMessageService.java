package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.domain.Model.Message;

import java.util.List;

public interface IMessageService {

    /**
      * Get all message between 2 users (userId1 and userId2)
      * @param userId1 Long
      * @param userId2 Long
    */

    List<Message> getMessagesBetweenUsers(Long userId1, Long userId2);
}
