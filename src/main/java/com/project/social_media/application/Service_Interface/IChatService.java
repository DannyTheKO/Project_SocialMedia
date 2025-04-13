package com.project.social_media.application.Service_Interface;

import com.project.social_media.domain.Model.Message;

public interface IChatService {

    /**
     * sendMessage
     *
     * @param message Message
    */
    Message sendMessage(Message message);


    /**
     * addUser
     *
     * @param message Message
    */
    Message addUser(Message message);
}
