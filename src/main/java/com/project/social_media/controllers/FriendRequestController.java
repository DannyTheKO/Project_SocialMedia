package com.project.social_media.controllers;


import com.project.social_media.application.DTO.FriendRequestDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IFriendRequestService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.ApiResponse.PageResponse;
import com.project.social_media.controllers.Request.FriendRequest.FriendRequestCreateRequest;
import com.project.social_media.controllers.Request.FriendRequest.FriendRequestUpdateRequest;
import com.project.social_media.domain.Model.JPA.FriendRequest;
import com.project.social_media.domain.Model.JPA.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

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
    public ResponseEntity<ApiResponse> getReceivedFriendRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            Page<FriendRequest> requestPage = friendRequestService.getReceivedFriendRequests(authUser.getUserId(),  PageRequest.of(page, size));
            List<FriendRequestDTO> requestDTOList = friendRequestService.convertToListDTO(requestPage.getContent());
            PageResponse pageResponse = new PageResponse(
                    requestDTOList, requestPage.getTotalPages(), requestPage.getTotalElements());
            return ResponseEntity.ok(new ApiResponse("Success", pageResponse));
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
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            // Check: from_user and to_user cannot be same person ( critical )
            if (authUser.getUserId().equals(request.getToUserId())) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("Error!", "Cannot send friend request to yourself"));
            }

            FriendRequest newRequest = friendRequestService.createFriendRequest(authUser.getUserId(), request.getToUserId());
            FriendRequestDTO requestDTO = friendRequestService.convertToDTO(newRequest);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Success", requestDTO));
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
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            FriendRequest existingRequest = friendRequestService.getFriendRequestById(friendRequestId);

            // Check: just send user can update request status
            if (!authUser.getUserId().equals(existingRequest.getToUser().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            // Status check: hide or prevent from updating friend-request are not PENDING
            if (existingRequest.getStatus() == FriendRequest.FriendRequestStatus.ACCEPTED ||
                    existingRequest.getStatus() == FriendRequest.FriendRequestStatus.REJECTED) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("Error!", "Cannot update a friend request that has already been accepted or rejected"));
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
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

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
