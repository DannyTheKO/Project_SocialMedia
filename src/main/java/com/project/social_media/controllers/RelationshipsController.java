package com.project.social_media.controllers;

import com.project.social_media.application.DTO.RelationshipsDTO;
import com.project.social_media.application.IService.IAuthenticationService;
import com.project.social_media.application.IService.IRelationshipsService;
import com.project.social_media.application.IService.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.ApiResponse.FriendshipCheck;
import com.project.social_media.controllers.Request.Relationships.BlockUserRequest;
import com.project.social_media.controllers.Request.Relationships.RelationshipsCreateRequest;
import com.project.social_media.domain.Model.JPA.Relationships;
import com.project.social_media.domain.Model.JPA.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/relationships")
public class RelationshipsController {
   /*
     Tạo quan hệ (thường sau khi chấp nhận yêu cầu kết bạn hoặc chặn).
     Cập nhật trạng thái quan hệ.
     Lấy danh sách bạn bè.
     Xóa quan hệ.
    */

    private final IRelationshipsService relationshipsService;
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    // GET danh sách bạn bè
    @GetMapping("/friends")
    public ResponseEntity<ApiResponse> getFriends() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            List<Relationships> friends = relationshipsService.getFriends(authUser.getUserId());
            List<RelationshipsDTO> friendDTOs = relationshipsService.convertToListDTO(friends, authUser.getUserId());
            return ResponseEntity.ok(new ApiResponse("Success", friendDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // CREATE quan hệ (thêm bạn bè/chặn)
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createRelationship(
            @RequestBody RelationshipsCreateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            if (authUser.getUserId().equals(request.getReceiverId())){
                return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Error", "Can not add friend to your self !"));
            }
            Relationships newRelationship = relationshipsService.createRelationship(authUser.getUserId(), request.getReceiverId(), request.getStatus());
            RelationshipsDTO relationshipDTO = relationshipsService.convertToDTO(newRelationship);
            return ResponseEntity.ok(new ApiResponse("Success", relationshipDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // Accept friend request
    @PostMapping("/accept")
    public ResponseEntity<ApiResponse> acceptFriendRequest(@RequestParam Long relationshipId){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            relationshipsService.acceptFriendRequest(relationshipId);
            return ResponseEntity.ok(new ApiResponse("Success", "Friend request accepted"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error !", e.getMessage()));
        }
    }

    // Reject friend request
    @PostMapping("/reject")
    public ResponseEntity<ApiResponse> rejectFriendRequest(@RequestParam Long relationshipId){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            relationshipsService.rejectFriendRequest(relationshipId);
            return ResponseEntity.ok(new ApiResponse("Success", "Friend request rejected"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/block")
    public ResponseEntity<ApiResponse> blockUser(@RequestBody BlockUserRequest request){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found !"));
            relationshipsService.blockUser(authUser.getUserId(), request.getUserId2());
            return ResponseEntity.ok(new ApiResponse("Success", "User blocked"));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error !", e.getMessage()));
        }
    }

    // DELETE quan hệ (hủy bạn bè/bỏ chặn)
    @DeleteMapping("/{relationshipId}/delete")
    public ResponseEntity<ApiResponse> deleteRelationship(
            @PathVariable Long relationshipId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName()).orElse(null);

            Relationships existingRelationship = relationshipsService.getRelationshipById(relationshipId);
            // Auth: chỉ một trong hai người dùng được xóa
            if (!authUser.getUserId().equals(existingRelationship.getUser1().getUserId()) &&
                    !authUser.getUserId().equals(existingRelationship.getUser2().getUserId()))
            {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            relationshipsService.deleteRelationship(relationshipId);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/check-friendship")
    public ResponseEntity<ApiResponse> checkFriendship(@RequestParam Long userId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            FriendshipCheck result = relationshipsService.checkFriendship(authUser.getUserId(), userId);
            return ResponseEntity.ok(new ApiResponse("Success", result));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/find-relationship-id")
    public ResponseEntity<ApiResponse> findRelationshipId(@RequestParam Long userId2) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Long relationshipId = relationshipsService.findRelationshipId(authUser.getUserId(), userId2);
            return ResponseEntity.ok(new ApiResponse("Success", relationshipId));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/find-pending-relationship-id")
    public ResponseEntity<ApiResponse> findPendingRelationshipId(@RequestParam Long userId2) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Long relationshipId = relationshipsService.findPendingRelationshipId(authUser.getUserId(), userId2);
            return ResponseEntity.ok(new ApiResponse("Success", relationshipId));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/pending-requests")
    public ResponseEntity<ApiResponse> getPendingRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.checkValidationAuth(authentication);
            User authUser = userService.getUserByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Relationships> pendingRequests = relationshipsService.getPendingRequests(authUser.getUserId());
            List<RelationshipsDTO> pendingRequestsDTOs = relationshipsService.convertToListDTO(pendingRequests, authUser.getUserId());
            return ResponseEntity.ok(new ApiResponse("Success", pendingRequestsDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }
}
