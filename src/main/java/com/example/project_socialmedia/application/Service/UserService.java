package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.DTO.UserDTO;
import com.example.project_socialmedia.application.Exception.ResourceConflict;
import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service_Interface.IUserService;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import com.example.project_socialmedia.infrastructure.Config.Request.User.UserCreateRequest;
import com.example.project_socialmedia.infrastructure.Config.Request.User.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
     * Create User
     *
     * @param createRequest Object {UserCreateRequest}
     */
    @Override
    public User createUser(UserCreateRequest createRequest) {
        User getUser = userRepository.findUserByEmail(createRequest.getEmail());
        if (getUser != null) {
            throw new ResourceConflict("createUser: user email already exists");
        }

        // Construct User
        User newUser = new User();
        newUser.setUsername(createRequest.getUsername());
        newUser.setFirstName(createRequest.getFirstname());
        newUser.setLastName(createRequest.getLastname());
        newUser.setEmail(createRequest.getEmail());
        newUser.setPassword(createRequest.getPassword());
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
    public User updateUser(Long userId, UserUpdateRequest request) throws IOException {
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

        if (request.getProfileImageUrl() != null && !request.getProfileImageUrl().isEmpty()) {
            String profileImageUrl = saveFile(request.getProfileImageUrl(), existingUser);
            existingUser.setProfileImageUrl(profileImageUrl);
        }

        if (request.getBannerImageUrl() != null && !request.getBannerImageUrl().isEmpty()) {
            String bannerImageUrl = saveFile(request.getBannerImageUrl(), existingUser);
            existingUser.setBannerImageUrl(bannerImageUrl);
        }

        // Save it in the database
        userRepository.save(existingUser);
        return existingUser;
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

    /**
     * Save File Function
     *
     * @param file Object {MultipartFile}
     * @param user Object {User}
     * @return String
     * @throws IOException
     */
    public String saveFile(MultipartFile file, User user) throws IOException {
        String fileName = user.getUserId() + "_"
                + UUID.randomUUID() + "_"
                + file.getOriginalFilename();

        // Create the full path to the file
        String uploadDir = "src/main/resources/uploads/user/images/";
        Path filePath = Paths.get(uploadDir, fileName);

        // Create the directory if it doesn't exist
        Files.createDirectories(filePath.getParent());

        // Save the file to the specified path
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }
}
