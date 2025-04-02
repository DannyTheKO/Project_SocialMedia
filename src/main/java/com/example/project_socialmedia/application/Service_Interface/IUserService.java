package com.example.project_socialmedia.application.Service_Interface;

import com.example.project_socialmedia.application.DTO.UserDTO;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.infrastructure.Request.User.UserCreateRequest;
import com.example.project_socialmedia.infrastructure.Request.User.UserUpdateRequest;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    /**
     * Get all User from database
     *
     * @return List of user
     */
    List<User> getAllUser();

    /**
     * Get User By ID
     *
     * @param userId User ID
     * @return User object
     */
    User getUserById(Long userId);


    /**
     * Create User
     *
     * @param createRequest UserCreateRequest Object
     */
    User createUser(UserCreateRequest createRequest);

    /**
     * Delete User By ID
     *
     * @param userId User ID
     */
    void deleteUser(Long userId);

    /**
     * Update User
     *
     * @param request UserUpdateRequest Object
     */
    User updateUser(Long userId, UserUpdateRequest request) throws IOException;

    /**
     * Convert User Object into UserDTO
     *
     * @param user Object {User}
     * @return Object {UserDTO}
     */
    UserDTO convertToDTO(User user);

    /**
     * Convert User List Object into UserDTO List Object
     *
     * @param userList List[T] {User}
     * @return List[T] {UserDTO}
     */
    List<UserDTO> convertToDTOList(List<User> userList);
}
