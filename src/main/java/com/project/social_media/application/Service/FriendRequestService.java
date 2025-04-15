package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.FriendRequestDTO;
import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.IService.IFriendRequestService;
import com.project.social_media.domain.Model.FriendRequest;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.FriendRequestRepository;
import com.project.social_media.domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService implements IFriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<FriendRequest> getReceivedFriendRequests(Long userId) {
        return friendRequestRepository.findByToUserUserIdAndStatus(userId, FriendRequest.FriendRequestStatus.PENDING);
    }

    @Override
    public FriendRequest getFriendRequestById(Long friendRequestId) {
        return friendRequestRepository.findById(friendRequestId)
                .orElseThrow(()-> new RuntimeException("Friend request not found"));
    }

    @Override
    public FriendRequest createFriendRequest(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(()-> new RuntimeException("From user not found"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(()-> new RuntimeException("To user not found"));

        if (friendRequestRepository.existsByFromUserUserIdAndToUserUserIdAndStatus(
                fromUserId, toUserId, FriendRequest.FriendRequestStatus.PENDING)) {
            throw new RuntimeException("Friend request already exist !");
        }

        FriendRequest request = new FriendRequest(
                fromUser, toUser, FriendRequest.FriendRequestStatus.PENDING, LocalDateTime.now());
        return friendRequestRepository.save(request);
    }

    @Override
    public FriendRequest updateFriendRequest(Long friendRequestId, FriendRequest.FriendRequestStatus status) {
        FriendRequest request = getFriendRequestById(friendRequestId);
        request.setStatus(status);
        return friendRequestRepository.save(request);
    }

    @Override
    public void deleteFriendRequest(Long friendRequestId) {
        FriendRequest request = getFriendRequestById(friendRequestId);
        friendRequestRepository.delete(request);
    }

    @Override
    public FriendRequestDTO convertToDTO(FriendRequest friendRequest) {
        FriendRequestDTO mappedDTO = modelMapper.map(friendRequest, FriendRequestDTO.class);

        mappedDTO.setFromUser(modelMapper.map(friendRequest.getFromUser(), UserDTO.class));
        mappedDTO.setToUser(modelMapper.map(friendRequest.getToUser(), UserDTO.class));
        return mappedDTO;
    }

    @Override
    public List<FriendRequestDTO> convertToListDTO(List<FriendRequest> friendRequests) {
        return friendRequests.stream().map(this::convertToDTO).toList();
    }
}
