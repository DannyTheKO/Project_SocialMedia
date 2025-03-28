package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.domain.Request.User.UserCreateRequest;
import com.example.project_socialmedia.domain.Request.User.UserUpdateRequest;

import java.util.List;

public interface IUserService {

    /**
     * Get all User from database
     * @return List of user
     */
    List<User> getAllUser();

    /**
     * Get User By ID
     * @param userId User ID
     * @return User object
     */
    User getUserById(Long userId);

    /**
     * Create User
     * @param request UserCreateRequest object
     */
    void createUser(UserCreateRequest request);

    /**
     * Delete User By ID
     * @param userId User ID
     */
    void deleteUser(Long userId);

    /**
     * Update User
     * @param request UserUpdateRequest Object
     */
    void updateUser(UserUpdateRequest request);
}
