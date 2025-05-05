package com.project.social_media.controllers;

import com.project.social_media.application.DTO.NotificationDTO;
import com.project.social_media.application.Exception.AuthenticationException;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.INotificationService;
import com.project.social_media.application.Service.UserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.domain.Model.JPA.User;
import com.project.social_media.domain.Model.MongoDB.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/notifications")
public class NotificationController {
    private final INotificationService notificationService;
    private final IAuthenticationService authenticationService;
    private final UserService userService;

    @MessageMapping("/notifications.send")
    public void sendNotification(Notification notification) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User receiverUser = userService.getUserByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        // Send notification to the user
        notificationService.sendNotificationToUser(notification);

        // Optionally, update the notification count
        notificationService.sendNotificationCountToUser(receiverUser.getUserId());
    }

    @MessageMapping("/notifications.markRead")
    public void markRead(String notificationId) {
        notificationService.markAsRead(notificationId);
    }

    /**
     * Get all notifications for the currently authenticated user
     */
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserNotification() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User existingUser = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFound("User not found"));

            List<NotificationDTO> notificationDTOList = notificationService.getUserNotifications(existingUser.getUserId());
            return ResponseEntity.ok(new ApiResponse("Success", notificationDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    /**
     * Count unread notifications for the current user
     */
    @GetMapping("/user/count")
    public ResponseEntity<ApiResponse> countUnreadNotifications() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User user = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFound("User not found"));

            long count = notificationService.countUnreadNotifications(user.getUserId());
            return ResponseEntity.ok(new ApiResponse("Success", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    /**
     * Mark a specific notification as read
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse> markAsRead(@PathVariable String notificationId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);

            notificationService.markAsRead(notificationId);
            return ResponseEntity.ok(new ApiResponse("Success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    /**
     * Mark all notifications as read for the current user
     */
    @PutMapping("/readAll")
    public ResponseEntity<ApiResponse> markAllAsRead() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User user = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFound("User not found"));

            notificationService.markAsReadAll(user.getUserId());
            return ResponseEntity.ok(new ApiResponse("Success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    /**
     * Delete a specific notification
     */
    @DeleteMapping("/{notificationId}/delete")
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable String notificationId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User user = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFound("User not found"));
            Notification notification = notificationService.getNotificationById(notificationId);

            // Fuck me
            if (!notification.getReceiverId().equals(user.getUserId())) {
                throw new AuthenticationException("You are not authorized to delete this notification");
            }

            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok(new ApiResponse("Success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    /**
     * Delete all notifications for the current user
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAllNotifications() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User user = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFound("User not found"));
            notificationService.deleteAllNotifications(user.getUserId());
            return ResponseEntity.ok(new ApiResponse("Success", "Deleted all notifications"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }
}
