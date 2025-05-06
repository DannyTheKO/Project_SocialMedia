package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.*;
import com.project.social_media.application.Exception.ResourceNotFound;
import com.project.social_media.application.IService.*;
import com.project.social_media.controllers.Request.SharedPost.SharedPostCreateRequest;
import com.project.social_media.controllers.Request.SharedPost.SharedPostUpdateRequest;
import com.project.social_media.domain.Model.JPA.Post;
import com.project.social_media.domain.Model.JPA.SharedPost;
import com.project.social_media.domain.Model.JPA.User;
import com.project.social_media.domain.Repository.JPA.PostRepository;
import com.project.social_media.domain.Repository.JPA.SharedPostRepository;
import com.project.social_media.domain.Repository.JPA.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SharedPostService implements ISharedPostService {

    private final ModelMapper modelMapper;

    private final SharedPostRepository sharedPostRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final IUserService userService;
    private final IPostService postService;
    private final IMediaService mediaService;
    private final ICommentService commentService;
    private final ILikeService likeService;

    /**
     * Get All Shared Posts
     *
     * @return List{Object} SharedPost
     */
    @Override
    public List<SharedPost> getAllSharedPosts() {
        return sharedPostRepository.findAll();
    }

    /**
     * Get Shared Post By ID
     *
     * @param sharedPostId Long
     * @return Object {SharedPost}
     */
    @Override
    public SharedPost getSharedPostById(Long sharedPostId) {
        return sharedPostRepository.findById(sharedPostId)
                .orElseThrow(() -> new ResourceNotFound("getSharedPostById: shared post not found"));
    }

    /**
     * Get All Shared Posts By User ID
     *
     * @param userId Long
     * @return List{Object} SharedPost
     */
    @Override
    public List<SharedPost> getAllSharedPostsByUserId(Long userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userId));
        return sharedPostRepository.findByUser(user);
    }

    /**
     * Create Shared Post
     *
     * @param request Object {SharedPostCreateRequest}
     * @param userId  Long
     * @return Object {SharedPost}
     */
    @Override
    public SharedPost createSharedPost(SharedPostCreateRequest request, Long userId) {
        try {
            // Check if User exists in the database
            User user = userRepository.findUserByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userId));

            // Check if original post exists
            Post originalPost = postService.getPostById(request.getOriginalPostId());
            if (originalPost == null) {
                throw new ResourceNotFound("createSharedPost: original post not found");
            }

            SharedPost sharedPost = new SharedPost();
            sharedPost.setUser(user);
            sharedPost.setOriginalPost(originalPost);
            sharedPost.setSharedContent(request.getSharedContent());
            sharedPost.setSharedAt(LocalDateTime.now());

            // Tăng shareCount của originalPost
            originalPost.setShareCount(originalPost.getShareCount() + 1);
            postRepository.save(originalPost);
            return sharedPostRepository.save(sharedPost);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Update Shared Post
     *
     * @param userId  Long
     * @param sharedPostId Long
     * @param request Object {SharedPostUpdateRequest}
     * @return Object {SharedPost}
     */
    @Override
    public SharedPost updateSharedPost(Long userId, Long sharedPostId, SharedPostUpdateRequest request) {
        try {
            SharedPost existingSharedPost = sharedPostRepository.findById(sharedPostId)
                    .orElseThrow(() -> new ResourceNotFound("updateSharedPost: Shared post not found"));

            existingSharedPost.setSharedContent(request.getSharedContent());
            return sharedPostRepository.save(existingSharedPost);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete Shared Post
     *
     * @param sharedPostId Long
     */
    @Override
    public void deleteSharedPost(Long sharedPostId) {
        try {
            SharedPost existingSharedPost = sharedPostRepository.findById(sharedPostId)
                    .orElseThrow(() -> new ResourceNotFound("deleteSharedPost: Shared post not found"));
            Post originalPost = existingSharedPost.getOriginalPost();
            if (originalPost.getShareCount() > 0) {
                originalPost.setShareCount(originalPost.getShareCount() - 1);
                postRepository.save(originalPost);
            }

            sharedPostRepository.delete(existingSharedPost);
        } catch (ResourceNotFound e) {
            throw new RuntimeException("Error deleting shared post: " + e.getMessage(), e);
        }
    }

    /**
     * Convert SharedPost to DTO
     *
     * @param sharedPost Object {SharedPost}
     * @return Object {SharedPostDTO}
     */
    @Override
    public SharedPostDTO convertToDTO(SharedPost sharedPost) {
        SharedPostDTO mappedSharedPostDTO = modelMapper.map(sharedPost, SharedPostDTO.class);

        mappedSharedPostDTO.setUser(userService.convertToDTO(sharedPost.getUser()));

        mappedSharedPostDTO.setOriginalPost(postService.convertToDTO(sharedPost.getOriginalPost()));

        mappedSharedPostDTO.setSharedAt(sharedPost.getSharedAt().toString());

        List<CommentDTO> commentDTOList = commentService.convertToDTOList(sharedPost.getComments());
        mappedSharedPostDTO.setComments(commentDTOList);

        // Set Like
        List<LikeDTO> likeDTOList = likeService.convertToDTOList(sharedPost.getLikes());
        mappedSharedPostDTO.setLikes(likeDTOList);

        // Set Media
        List<MediaDTO> mediaDTOList = mediaService.getMediaDTOByTargetIdAndTargetType(sharedPost.getOriginalPost().getPostId(), "Post");
        mappedSharedPostDTO.setMedia(mediaDTOList);
        return mappedSharedPostDTO;
    }

    /**
     * Convert List of SharedPost to List of DTO
     *
     * @param sharedPosts List{Object} SharedPost
     * @return List{Object} SharedPostDTO
     */
    @Override
    public List<SharedPostDTO> convertToListDTO(List<SharedPost> sharedPosts) {
        return sharedPosts.stream().map(this::convertToDTO).toList();
    }

    /**
     * Count Shares By Original Post
     *
     * @param originalPostId Long
     * @return long
     */
    public long countSharesByOriginalPostId(Long originalPostId) {
        Post originalPost = postService.getPostById(originalPostId);
        if (originalPost == null) {
            throw new ResourceNotFound("countSharesByOriginalPostId: original post not found");
        }
        return sharedPostRepository.countSharesByOriginalPost(originalPost);
    }

    /**
     * Get Timeline (Combined Posts and Shared Posts)
     *
     * @param userId Long
     * @return List{Object} containing PostDTO and SharedPostDTO
     */
    public List<Object> getTimeline() {

        List<Post> posts = postService.getAllPosts();
        List<PostDTO> postDTOs = postService.convertToListDTO(posts);


        List<SharedPost> sharedPosts = getAllSharedPosts();
        List<SharedPostDTO> sharedPostDTOs = convertToListDTO(sharedPosts);


        List<Object> timeline = new ArrayList<>();
        timeline.addAll(postDTOs);
        timeline.addAll(sharedPostDTOs);
        timeline.sort((a, b) -> {
            String timeA = (a instanceof PostDTO) ? String.valueOf(((PostDTO) a).getCreatedPost()) : ((SharedPostDTO) a).getSharedAt();
            String timeB = (b instanceof PostDTO) ? String.valueOf(((PostDTO) b).getCreatedPost()) : ((SharedPostDTO) b).getSharedAt();
            return timeB.compareTo(timeA);
        });

        return timeline;
    }
}