package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.domain.Model.Message;

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
