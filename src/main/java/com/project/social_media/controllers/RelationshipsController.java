package com.project.social_media.controllers;

import com.project.social_media.application.DTO.RelationshipsDTO;
import com.project.social_media.application.Service_Interface.IAuthenticationService;
import com.project.social_media.application.Service_Interface.IRelationshipsService;
import com.project.social_media.application.Service_Interface.IUserService;
import com.project.social_media.controllers.ApiResponse.ApiResponse;
import com.project.social_media.controllers.Request.Relationships.RelationshipsCreateRequest;
import com.project.social_media.controllers.Request.Relationships.RelationshipsUpdateRequest;
import com.project.social_media.domain.Model.Relationships;
import com.project.social_media.domain.Model.User;
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
    public ResponseEntity<ApiResponse> getFriends(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            List<Relationships> friends = relationshipsService.getFriends(authUser.getUserId());
            List<RelationshipsDTO> friendDTOs = relationshipsService.convertToListDTO(friends);
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
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            Relationships newRelationship = relationshipsService.createRelationship(authUser.getUserId(),
                    request.getUserId2(),
                    request.getStatus());
            RelationshipsDTO relationshipDTO = relationshipsService.convertToDTO(newRelationship);
            return ResponseEntity.ok(new ApiResponse("Success", relationshipDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // UPDATE trạng thái quan hệ
    @PutMapping("/{relationshipId}/update")
    public ResponseEntity<ApiResponse> updateRelationship(
            @PathVariable Long relationshipId,
            @RequestBody RelationshipsUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

            Relationships existingRelationship = relationshipsService.getRelationshipById(relationshipId);
            // Auth: chỉ một trong hai người dùng được cập nhật
            if (!authUser.getUserId().equals(existingRelationship.getUser1().getUserId()) &&
                    !authUser.getUserId().equals(existingRelationship.getUser2().getUserId())) {
                return ResponseEntity.status(UNAUTHORIZED)
                        .body(new ApiResponse("Invalid Permission", null));
            }

            Relationships updatedRelationship = relationshipsService.updateRelationship(relationshipId, request.getStatus());
            RelationshipsDTO relationshipDTO = relationshipsService.convertToDTO(updatedRelationship);
            return ResponseEntity.ok(new ApiResponse("Success", relationshipDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    // DELETE quan hệ (hủy bạn bè/bỏ chặn)
    @DeleteMapping("/{relationshipId}/delete")
    public ResponseEntity<ApiResponse> deleteRelationship(
            @PathVariable Long relationshipId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authenticationService.authenticationCheck(authentication);
            User authUser = userService.getUserByUsername(authentication.getName());

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

}
