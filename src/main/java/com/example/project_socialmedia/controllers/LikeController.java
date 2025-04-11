package com.example.project_socialmedia.controllers;

import com.example.project_socialmedia.application.DTO.LikeDTO;
import com.example.project_socialmedia.application.Service.LikeService;
import com.example.project_socialmedia.controllers.ApiResponse.ApiResponse;
import com.example.project_socialmedia.controllers.Request.Like.LikeRequest;
import com.example.project_socialmedia.domain.Model.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/likes")
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/post")
    public ResponseEntity<ApiResponse> getAllLikesByPostId(@RequestParam Long postId) {
        try {
            List<Like> likes = likeService.getAllLikeByPostId(postId);
            List<LikeDTO> likeDTOS = likeService.convertToDTOList(likes);
            return ResponseEntity.ok(new ApiResponse("Success", likeDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<ApiResponse> getAllLikesByCommentId(@RequestParam Long commentId) {
        try {
            List<Like> likes = likeService.getAllLikeByCommentId(commentId);
            List<LikeDTO> likeDTOS = likeService.convertToDTOList(likes);
            return ResponseEntity.ok(new ApiResponse("Success", likeDTOS));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/count/post")
    public ResponseEntity<ApiResponse> getLikesCountByPostId(@RequestParam Long postId) {
        try {
            Integer likeCount = likeService.getLikeCountByPostId(postId);
            return ResponseEntity.ok(new ApiResponse("Success", likeCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/count/comment")
    public ResponseEntity<ApiResponse> getLikesCountByCommentId(@RequestParam Long commentId) {
        try {
            Integer likeCount = likeService.getLikeCountByCommentId(commentId);
            return ResponseEntity.ok(new ApiResponse("Success", likeCount));
        }  catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PutMapping("/like")
    public ResponseEntity<ApiResponse> toggleLike(
            @RequestParam Long userId,
            @RequestParam(required = false) Long postId,
            @RequestParam(required = false) Long commentId,
            @ModelAttribute LikeRequest request) {
        try {
            request.setPostId(postId);
            request.setCommentId(commentId);
            likeService.toggleLike(userId, request);

            return ResponseEntity.ok(new ApiResponse("Success", true));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }
}
