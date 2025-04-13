package com.example.project_socialmedia.application.Service;

import com.example.project_socialmedia.application.DTO.LikeDTO;
import com.example.project_socialmedia.application.Service_Interface.ILikeService;
import com.example.project_socialmedia.controllers.Request.Like.LikeRequest;
import com.example.project_socialmedia.domain.Model.Comment;
import com.example.project_socialmedia.domain.Model.Like;
import com.example.project_socialmedia.domain.Model.Post;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.CommentRepository;
import com.example.project_socialmedia.domain.Repository.LikeRepository;
import com.example.project_socialmedia.domain.Repository.PostRepository;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService {
    private final ModelMapper modelMapper;

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * Get all like by postId
     *
     * @param postId Long
     * @return List{Object}
     */
    @Override
    public List<Like> getAllLikeByPostId(Long postId) {
        Post existingPost = postRepository.findPostByPostId(postId);
        return existingPost.getLikes();
    }

    /**
     * Get all like by commentId
     *
     * @param commentId Long
     * @return List{Object}
     */
    @Override
    public List<Like> getAllLikeByCommentId(Long commentId) {
        Comment existingComment = commentRepository.findCommentByCommentId(commentId);
        if  (existingComment != null) {
            return existingComment.getLikes();
        }

        return null;
    }

    /**
     * Count like by postId
     *
     * @param postId Long
     * @return Integer
     */
    @Override
    public Integer getLikeCountByPostId(Long postId) {
        Post existingPost = postRepository.findPostByPostId(postId);
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
        Comment existingComment = commentRepository.findCommentByCommentId(commentId);
        return existingComment.getLikes().size();
    }

    /**
     * Toggle Like
     *
     * @param userId Long
     * @param request {LikeRequest}
     */
    @Override
    public void toggleLike(Long userId, LikeRequest request) throws RuntimeException {
        User existingUser = userRepository.findUserByUserId(userId);

        if (request.getPostId() != null) {
            Post existingPost = postRepository.findPostByPostId(request.getPostId());
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

                likeRepository.save(newLike);

            }
        } else if (request.getCommentId() != null) { // request.getCommentId != null

            Comment existingComment = commentRepository.findCommentByCommentId(request.getCommentId());

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
