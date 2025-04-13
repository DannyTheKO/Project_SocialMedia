package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.Exception.ResourceConflict;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.Service_Interface.IMediaService;
import com.project.social_media.application.Service_Interface.IUserService;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.controllers.Request.User.UserUpdateRequest;
import com.project.social_media.domain.Model.Media;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    final String uploadDir = "gui/src/asset/uploads/users/";
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final IMediaService mediaService;

    /**
     * Get all User from database
     *
     * @return List of user
     */
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Get User By ID
     *
     * @param userId User ID
     * @return User object
     */
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("getUserById: userId not found"));
    }

    /**
     * Get User By Username
     *
     * @param username String
     * @return {User}
     */
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    /**
     * Get User By Email
     *
     * @param email String
     * @return {User}
     */
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Create User
     *
     * @param request Object {UserCreateRequest}
     */
    @Override
    public User createUser(UserCreateRequest request) {
        User byEmail = userRepository.findUserByEmail(request.getEmail());
        User byUsername = userRepository.findUserByUsername(request.getUsername());

        // Validation
        if (byEmail != null) {
            throw new ResourceConflict("createUser: user email already exists");
        }

        if (byUsername != null) {
            throw new ResourceConflict("createUser: username already exists");
        }

        // Construct User
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setFirstName(request.getFirstname());
        newUser.setLastName(request.getLastname());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());

        // Send to database
        userRepository.save(newUser);
        return newUser;
    }

    /**
     * Delete User By ID
     *
     * @param userId Long
     */
    @Override
    public void deleteUser(Long userId) {
        // Get User by ID
        userRepository.findById(userId)
                // If found, delete it
                .ifPresentOrElse(userRepository::delete, () -> {
                    // Else, return exception UserNotFound
                    throw new ResourceNotFound("deleteUser: userId not found");
                });
    }

    /**
     * Update User
     *
     * @param request Object {UserUpdateRequest}
     */
    @Override
    public User updateUser(Long userId, UserUpdateRequest request) {
        try {
            // Get User though UserID
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFound("updateUser: userId not found"));

            // Update User by overwriting from request
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            existingUser.setEmail(request.getEmail());
            existingUser.setPassword(request.getPassword());
            existingUser.setBio(request.getBio());
            existingUser.setBirthDate(request.getBirthDate());

            if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
                String fileType = mediaService.identifyMediaType(Objects.requireNonNull(request.getProfileImage().getOriginalFilename()));

                // Remove old file
                mediaService.removeFile(userId, "ProfileImage", fileType);

                // Then added new one
                Media profileImage = mediaService.saveFile(
                        request.getProfileImage(),
                        uploadDir + existingUser.getUserId() + "/",
                        existingUser.getUserId(),
                        "ProfileImage"
                );
                existingUser.setProfileImageUrl(profileImage.getFilePath());
            }

            if (request.getBannerImage() != null && !request.getBannerImage().isEmpty()) {
                String fileType = mediaService.identifyMediaType(Objects.requireNonNull(request.getBannerImage().getOriginalFilename()));

                // Remove old file
                mediaService.removeFile(userId, "BannerImage", fileType);

                // Then added new one
                Media bannerImage = mediaService.saveFile(
                        request.getBannerImage(),
                        uploadDir + existingUser.getUserId() + "/",
                        existingUser.getUserId(),
                        "BannerImage"
                );
                existingUser.setBannerImageUrl(bannerImage.getFilePath());
            }

            // Save it in the database
            userRepository.save(existingUser);
            return existingUser;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert User Object into UserDTO
     *
     * @param user Object {User}
     * @return Object {UserDTO}
     */
    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Convert User List Object into UserDTO List Object
     *
     * @param userList List[T] {User}
     * @return List[T] {UserDTO}
     */
    public List<UserDTO> convertToDTOList(List<User> userList) {
        return userList.stream().map(this::convertToDTO).toList();
    }

}
