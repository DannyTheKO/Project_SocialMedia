package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.LikeDTO;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.IService.ILikeService;
import com.project.social_media.controllers.Request.Like.LikeRequest;
import com.project.social_media.domain.Model.JPA.Comment;
import com.project.social_media.domain.Model.JPA.Like;
import com.project.social_media.domain.Model.JPA.Post;
import com.project.social_media.domain.Model.JPA.User;
import com.project.social_media.domain.Repository.JPA.CommentRepository;
import com.project.social_media.domain.Repository.JPA.LikeRepository;
import com.project.social_media.domain.Repository.JPA.PostRepository;
import com.project.social_media.domain.Repository.JPA.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService {
    private final ModelMapper modelMapper;

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final NotificationService notificationService;

    /**
     * Get all like by postId
     *
     * @param postId Long
     * @return List{Object}
     */
    @Override
    public List<Like> getAllLikeByPostId(Long postId) {
        Post existingPost = postRepository.findPostByPostId(postId)
                .orElse(new Post());

        return existingPost.getLikes();
    }

    /**
     * Get all likes by commentId
     *
     * @param commentId Long
     * @return List{Object}
     */
    @Override
    public List<Like> getAllLikeByCommentId(Long commentId) {
        Comment existingComment = commentRepository.findCommentByCommentId(commentId)
                .orElse(new Comment()); // Return empty Comment object

        return existingComment.getLikes() != null ? existingComment.getLikes() : new ArrayList<>();
    }


    /**
     * Count like by postId
     *
     * @param postId Long
     * @return Integer
     */
    @Override
    public Integer getLikeCountByPostId(Long postId) {
        Post existingPost = postRepository.findPostByPostId(postId)
                .orElse(new Post());

        return existingPost.getLikes().size();
    }

    /**
     * Count like by commentId
     *
     * @param commentId Long
     * @return Integer
     */
    @Override
    public Integer getLikeCountByCommentId(Long commentId) {
        Comment existingComment = commentRepository.findCommentByCommentId(commentId)
                .orElse(new Comment());

        return existingComment.getLikes() != null ? existingComment.getLikes().size() : 0;
    }

    /**
     * Toggle Like
     *
     * @param userId  Long
     * @param request {LikeRequest}
     */
    @Override
    public void toggleLike(Long userId, LikeRequest request) throws RuntimeException {
        User existingUser = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("toggleLike: userId not found"));

        if (request.getPostId() != null) {
            Post existingPost = postRepository.findPostByPostId(request.getPostId())
                    .orElseThrow(() -> new ResourceNotFound("toggleLike: postId not found"));

            Like existingLike = likeRepository.findLikesByPost(existingPost)
                    .stream()
                    .filter(like -> like.getUser().getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);

            if (existingLike != null) {
                // Unlike
                likeRepository.delete(existingLike);
            } else {
                // Like
                Like newLike = new Like();
                newLike.setUser(existingUser);
                newLike.setPost(existingPost);
                newLike.setCreatedAt(LocalDateTime.now());

                // FIXME: need maintained and testing
                notificationService.createLikePostNotification(userId, existingPost.getUser().getUserId(), existingPost.getPostId());
                likeRepository.save(newLike);

            }
        } else if (request.getCommentId() != null) { // request.getCommentId != null

            Comment existingComment = commentRepository.findCommentByCommentId(request.getCommentId())
                    .orElseThrow(() -> new ResourceNotFound("toggleLike: commentId not found"));

            Like existingLike = likeRepository.findLikesByComment(existingComment)
                    .stream()
                    .filter(like -> like.getUser().getUserId().equals(userId)).findFirst().orElse(null);

            if (existingLike != null) {
                likeRepository.delete(existingLike);
            } else {
                Like newLike = new Like();
                newLike.setUser(existingUser);
                newLike.setComment(existingComment);
                newLike.setCreatedAt(LocalDateTime.now());

                // FIXME: Add notification for like comment type
                likeRepository.save(newLike);
            }
        }
    }


    public LikeDTO convertToDTO(Like like) {
        LikeDTO likeDTO = modelMapper.map(like, LikeDTO.class);

        // Set UserID
        likeDTO.setUserId(like.getUser().getUserId());
        likeDTO.setUsername(like.getUser().getUsername());
        likeDTO.setFirstname(like.getUser().getFirstName());
        likeDTO.setLastname(like.getUser().getLastName());

        // Set PostID or CommentID
        if (like.getPost() != null) {
            likeDTO.setPostId(like.getPost().getPostId());
        }

        if (like.getComment() != null) {
            likeDTO.setCommentId(like.getComment().getCommentId());
        }

        likeDTO.setCreatedAt(like.getCreatedAt());

        return likeDTO;
    }

    public List<LikeDTO> convertToDTOList(List<Like> likeList) {
        return likeList.stream().map(this::convertToDTO).toList();
    }
}
