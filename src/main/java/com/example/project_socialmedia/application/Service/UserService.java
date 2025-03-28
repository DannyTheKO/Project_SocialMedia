package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.Service_Interface.IUserService;
import com.example.project_socialmedia.domain.Modal.User;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import com.example.project_socialmedia.domain.Request.User.UserCreateRequest;
import com.example.project_socialmedia.domain.Request.User.UserUpdateRequest;
import com.example.project_socialmedia.infrastructure.Exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

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
                .orElseThrow(() -> new UserNotFound("getUserById: userId not found"));
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
                new Date(), // Created At;
                new Date()  // Last Login;
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
                    throw new UserNotFound("deleteUser: userId not found");
                });
    }

    /**
     * TODO: Update User
     *
     * @param request request Object
     */
    @Override
    public void updateUser(UserUpdateRequest request) {

    }
}
