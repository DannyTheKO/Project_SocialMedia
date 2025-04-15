package com.project.social_media.controllers;


import com.project.social_media.application.DTO.FriendRequestDTO;
import com.project.social_media.application.Service_Interface.IAuthenticationService;
import com.project.social_media.application.Service_Interface.IFriendRequestService;
import com.project.social_media.application.Service_Interface.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.FriendRequest.FriendRequestCreateRequest;
import com.project.social_media.controllers.Request.FriendRequest.FriendRequestUpdateRequest;
import com.project.social_media.domain.Model.FriendRequest;
import com.project.social_media.domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/friend-requests")
public class FriendRequestController {
  /*
    Gửi yêu cầu kết bạn.
    Chấp nhận/từ chối yêu cầu.
    Lấy danh sách yêu cầu kết bạn nhận được.
   */

    private final IFriendRequestService friendRequestService;
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    // GET tất cả yêu cầu kết bạn
    @GetMapping("/received")
    public ResponseEntity<ApiResponse> getReceivedFriendRequests(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            List<FriendRequest> requestList = friendRequestService.getReceivedFriendRequests(authUser.getUserId());
            List<FriendRequestDTO> requestDTOList = friendRequestService.convertToListDTO(requestList);
            return ResponseEntity.ok(new ApiResponse("Success", requestDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // CREATE yêu cầu kết bạn
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createFriendRequest(
            @RequestBody FriendRequestCreateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            FriendRequest newRequest = friendRequestService.createFriendRequest(authUser.getUserId(), request.getToUserId());
            FriendRequestDTO requestDTO = friendRequestService.convertToDTO(newRequest);
            return ResponseEntity.ok(new ApiResponse("Success", requestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // UPDATE trạng thái yêu cầu kết bạn (ACCEPT/REJECT)
    @PutMapping("/{friendRequestId}/update")
    public ResponseEntity<ApiResponse> updateFriendRequest(
            @PathVariable Long friendRequestId,
            @RequestBody FriendRequestUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            FriendRequest existingRequest = friendRequestService.getFriendRequestById(friendRequestId);

            // Kiểm tra auth: chỉ người nhận yêu cầu được cập nhật
            if (!authUser.getUserId().equals(existingRequest.getToUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            FriendRequest updatedRequest = friendRequestService.updateFriendRequest(friendRequestId, request.getStatus());
            FriendRequestDTO requestDTO = friendRequestService.convertToDTO(updatedRequest);
            return ResponseEntity.ok(new ApiResponse("Success", requestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // DELETE yêu cầu kết bạn
    @DeleteMapping("/{friendRequestId}/delete")
    public ResponseEntity<ApiResponse> deleteFriendRequest(
            @PathVariable Long friendRequestId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            FriendRequest existingRequest = friendRequestService.getFriendRequestById(friendRequestId);
            // Kiểm tra auth: chỉ người gửi hoặc nhận được xóa
            if (!authUser.getUserId().equals(existingRequest.getFromUser().getUserId()) &&
                    !authUser.getUserId().equals(existingRequest.getToUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            friendRequestService.deleteFriendRequest(friendRequestId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
