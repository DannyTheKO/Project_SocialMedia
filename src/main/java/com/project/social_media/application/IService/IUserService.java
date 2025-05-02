package com.project.social_media.application.IService;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.controllers.Request.User.UserUpdateRequest;
import com.project.social_media.domain.Model.JPA.User;

import java.util.List;
import java.util.Optional;

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
     * Get User By Username
     *
     * @param username String
     * @return {User}
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Get User By Email
     *
     * @param email String
     * @return {User}
     */
    User getUserByEmail(String email);

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
    User updateUser(Long userId, UserUpdateRequest request);

    /**
     * Admin: Update Role and State User
     *
     * @param userId UserID
     * @param role {@link User.userRole}
     * @param state {@link User.userState}
     */
    User updateUserRoleAndState(Long userId, User.userRole role, User.userState state);

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
