package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.RelationshipsDTO;
import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.IService.INotificationService;
import com.project.social_media.application.IService.IRelationshipsService;
import com.project.social_media.controllers.ApiResponse.FriendshipCheck;
import com.project.social_media.domain.Model.JPA.Relationships;
import com.project.social_media.domain.Model.JPA.User;
import com.project.social_media.domain.Repository.JPA.RelationshipsRepository;
import com.project.social_media.domain.Repository.JPA.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelationshipsService implements IRelationshipsService {
    private final ModelMapper modelMapper;

    private final RelationshipsRepository relationshipsRepository;
    private final UserRepository userRepository;

    private final INotificationService notificationService;


    @Override
    public List<Relationships> getFriends(Long userId) {

        List<Relationships> friends = relationshipsRepository
                .findByUserIdAndStatus(userId, Relationships.RelationshipStatus.FRIENDS);
        // filter BLOCKED user
        return friends.stream()
                .filter(r -> {
                    Optional<Relationships> blocked = relationshipsRepository.findByUserIds(
                            r.getUser1().getUserId(), r.getUser2().getUserId());
                    return blocked.map(rel -> rel.getStatus() != Relationships.RelationshipStatus.BLOCKED)
                            .orElse(true);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Relationships> getPendingRequests(Long userId) {

        List<Relationships> friends = relationshipsRepository
                .findByUserIdAndStatus(userId, Relationships.RelationshipStatus.PENDING);
        // filter BLOCKED user
        return friends.stream()
                .filter(r -> {
                    Optional<Relationships> blocked = relationshipsRepository.findByUserIds(
                            r.getUser1().getUserId(), r.getUser2().getUserId());
                    return blocked.map(rel -> rel.getStatus() != Relationships.RelationshipStatus.BLOCKED)
                            .orElse(true);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Relationships getRelationshipById(Long relationshipId) {
        return relationshipsRepository.findById(relationshipId).orElseThrow(() -> new RuntimeException("Relationship not found"));
    }

    @Override
    public Relationships createRelationship(Long senderId, Long receiverId, Relationships.RelationshipStatus status) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (relationshipsRepository.existsByUser1UserIdAndUser2UserIdAndStatus(sender.getUserId(), receiver.getUserId(), status)){
            throw new RuntimeException("Relationship already exists");
        }

        Relationships relationship = new Relationships(sender, receiver, status, LocalDateTime.now(), LocalDateTime.now());
        Relationships updatedRelationship = relationshipsRepository.save(relationship);
        notificationService.createFriendRequestNotification(sender.getUserId(), receiver.getUserId(), updatedRelationship.getRelationshipId());
        return updatedRelationship;
    }

    @Override
    public void acceptFriendRequest(Long relationshipId){
        Relationships relationship = relationshipsRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found !"));
        if (relationship.getStatus() != Relationships.RelationshipStatus.PENDING){
            throw new RuntimeException("Not a pending friend request");
        }

        relationship.updateRelationshipStatus(Relationships.RelationshipStatus.FRIENDS, LocalDateTime.now());
        relationshipsRepository.save(relationship);
    }

    @Override
    public void rejectFriendRequest(Long relationshipId){
        Relationships relationship = relationshipsRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationships not found !"));
        if (relationship.getStatus() != Relationships.RelationshipStatus.PENDING) {
            throw new RuntimeException("Not a pending friend request");
        }
        relationshipsRepository.delete(relationship);
    }

    @Override
    public void blockUser(Long userId1, Long userId2){
        User user1 = userRepository.findById(userId1)
                .orElseThrow(()-> new RuntimeException("User not found !"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(()-> new RuntimeException("User not found !"));

        Optional<Relationships> existing = relationshipsRepository.findByUserIds(userId1, userId2);
        Relationships relationship;
        if (existing.isPresent()){
            relationship = existing.get();
            relationship.updateRelationshipStatus(Relationships.RelationshipStatus.BLOCKED, LocalDateTime.now());
        }
        else {
            relationship = new Relationships(user1, user2,
                    Relationships.RelationshipStatus.BLOCKED,
                    LocalDateTime.now(),
                    LocalDateTime.now());
        }
        relationshipsRepository.save(relationship);
    }

    @Override
    public void deleteRelationship(Long relationshipId) {
        Relationships relationship = getRelationshipById(relationshipId);
        relationshipsRepository.delete(relationship);
    }

    @Override
    public FriendshipCheck checkFriendship(Long userId1, Long userId2) {
        if (userId1.equals(userId2)) {
            return new FriendshipCheck(false, false, false, null); // can't be friend to self
        }

        // Check FRIENDS status
        Optional<Relationships> relationship = relationshipsRepository.findByUserIds(userId1, userId2);
        if (relationship.isPresent()){
            Relationships r = relationship.get();
            boolean isFriend = r.getStatus() == Relationships.RelationshipStatus.FRIENDS;
            boolean isPending = r.getStatus() == Relationships.RelationshipStatus.PENDING;
            boolean isBlocked = r.getStatus() == Relationships.RelationshipStatus.BLOCKED;

            return new FriendshipCheck(isFriend, isPending, isBlocked, r.getRelationshipId());
        }
        return new FriendshipCheck(false, false, false, null);
    }

    @Override
    public Long findRelationshipId(Long userId1, Long userId2) {
        Optional<Relationships> relationship = relationshipsRepository.findByUserIds(userId1, userId2);
        return relationship.map(Relationships::getRelationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));
    }

    @Override
    public Long findPendingRelationshipId(Long userId1, Long userId2){
        Optional<Relationships> relationship = relationshipsRepository.
                findPendingRequest(userId1, userId2, Relationships.RelationshipStatus.PENDING);
        return relationship.map(Relationships::getRelationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));
    }

//    @Override
//    public Page<Relationships> getPendingRequests(Long userId, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        return relationshipsRepository.findPendingRequestsByUserId(userId, pageable);
//    }

    @Override
    public List<RelationshipsDTO> convertToListDTO(List<Relationships> relationships, Long currentUserId) {
        return relationships.stream().map(
                r -> convertToDTO(r, currentUserId)).toList();
    }

    @Override
    public RelationshipsDTO convertToDTO(Relationships relationship) {
        RelationshipsDTO mappedDTO = modelMapper.map(relationship, RelationshipsDTO.class);

        mappedDTO.setUser1(modelMapper.map(relationship.getUser1(), UserDTO.class));
        mappedDTO.setUser2(modelMapper.map(relationship.getUser2(), UserDTO.class));
        return mappedDTO;
    }

    @Override
    public RelationshipsDTO convertToDTO(Relationships relationship, Long currentUserId) {
        // Get currentUser info
        RelationshipsDTO dto = new RelationshipsDTO();
        dto.setRelationshipId(relationship.getRelationshipId());
        dto.setStatus(relationship.getStatus());
        dto.setCreatedAt(relationship.getCreatedAt());
        dto.setUpdatedAt(relationship.getUpdatedAt());

        // Ensure user1 is currentUser, user2 is other one
        if (currentUserId.equals(relationship.getUser1().getUserId())) {
            dto.setUser1(convertUserToDTO(relationship.getUser1()));
            dto.setUser2(convertUserToDTO(relationship.getUser2()));
        } else {
            dto.setUser1(convertUserToDTO(relationship.getUser2()));
            dto.setUser2(convertUserToDTO(relationship.getUser1()));
        }

        return dto;
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId().toString());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setBio(user.getBio());
        userDTO.setBirthDate(user.getBirthDay());
        userDTO.setProfileImageUrl(user.getProfileImageUrl());
        userDTO.setBannerImageUrl(user.getBannerImageUrl());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLastLogin(user.getLastLogin());
        return userDTO;
    }
}
