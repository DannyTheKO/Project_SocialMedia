package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Service_Interface.IUserService;
import com.example.project_socialmedia.controllers.DTO.UserDTO;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import com.example.project_socialmedia.application.Request.User.UserCreateRequest;
import com.example.project_socialmedia.application.Request.User.UserUpdateRequest;
import com.example.project_socialmedia.controllers.Exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param request UserCreateRequest object
     */
    @Override
    public void createUser(UserCreateRequest request) {
        // Construct User
        User newUser = new User(
                request.getUsername(),
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword(),
                LocalDateTime.now(), // Created At;
                LocalDateTime.now()  // Last Login;
        );

        // Send to database
        userRepository.save(newUser);
    }

    /**
     * Delete User By ID
     *
     * @param userId User ID
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
     * @param request request Object
     */
    @Override
    public void updateUser(UserUpdateRequest request, Long userId) {
        // Get User though UserID
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("updateUser: userId not found"));

        // Update User by overwriting from request
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setEmail(request.getEmail());
        existingUser.setPassword(request.getPassword());

        // Save it in the database
        userRepository.save(existingUser);
    }

    // TODO: Convert User Object Into UserDTO Object
    // Use ModelMapper to map User object
    /**
     * Convert User Object into UserDTO
     * @param user  Object {User}
     * @return      Object {UserDTO}
     */
    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Convert User List Object into UserDTO List Object
     * @param userList  List[T] {User}
     * @return          List[T] {UserDTO}
     */
    public List<UserDTO> convertToDTOList(List<User> userList) {
        return userList.stream().map(this::convertToDTO).toList();
    }
}
