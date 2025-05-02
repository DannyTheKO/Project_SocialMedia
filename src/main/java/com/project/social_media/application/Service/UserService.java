package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.Exception.ResourceConflict;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.IService.IMediaService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.Request.User.UserCreateRequest;
import com.project.social_media.controllers.Request.User.UserUpdateRequest;
import com.project.social_media.domain.Model.JPA.Media;
import com.project.social_media.domain.Model.JPA.User;
import com.project.social_media.domain.Repository.JPA.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final IMediaService mediaService;

    final String uploadDir = "gui/src/Assets/uploads/users/";

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
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get User By Email
     *
     * @param email String
     * @return {User}
     */
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("getUserByEmail: email not found"));
    }

    /**
     * Create User
     *
     * @param request Object {UserCreateRequest}
     */
    @Override
    public User createUser(UserCreateRequest request) {
        // Validation
        userRepository.findUserByEmail(request.getEmail())
                .ifPresent(email -> {
                    throw new ResourceConflict("Email '" + request.getEmail() + "' already exists");
                });

        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new ResourceConflict("Username '" + request.getUsername() + "' already exists");
                });

        // Construct User
        User newUser = new User();
        newUser.setUserRole(User.userRole.USER);        // Default Role
        newUser.setUserState(User.userState.ACTIVE);    // Default State
        newUser.setUsername(request.getUsername());
        newUser.setFirstName(request.getFirstname());
        newUser.setLastName(request.getLastname());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());

        if (request.getBirthday() != null && !request.getBirthday().isEmpty()) {
            try {
                LocalDateTime birthday = LocalDateTime.parse(request.getBirthday());
                newUser.setBirthDay(birthday);
            } catch (DateTimeParseException e) {
               System.out.println("Unexpected error when create user with birthday: " + e.getMessage());
            }
        }

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

            // Update only fields that are provided
            if (request.getFirstName() != null) {
                existingUser.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                existingUser.setLastName(request.getLastName());
            }
            if (request.getEmail() != null) {
                existingUser.setEmail(request.getEmail());
            }
            if (request.getPassword() != null) {
                existingUser.setPassword(request.getPassword());
            }
            if (request.getBio() != null) {
                existingUser.setBio(request.getBio());
            }
            if (request.getBirthDate() != null) {
                existingUser.setBirthDay(request.getBirthDate());
            }

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
     * Admin: Update User Role And State
     *
     * @param userId UserID
     * @param role {@link User.userRole}
     * @param state {@link User.userState}
     * @return {@link User}
     */
    @Override
    public User updateUserRoleAndState(Long userId, User.userRole role, User.userState state) {
        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFound("updateUserRoleAndState: userId not found"));

            // Update only fields that are provided
            if (existingUser.getUserRole() != role) {
                existingUser.setUserRole(role);
            }

            if (existingUser.getUserState() != state) {
                existingUser.setUserState(state);
            }

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
