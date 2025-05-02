package com.project.social_media.application.Service;

import com.project.social_media.application.DTO.RelationshipsDTO;
import com.project.social_media.application.DTO.UserDTO;
import com.project.social_media.application.IService.IRelationshipsService;
import com.project.social_media.controllers.ApiResponse.FriendshipCheck;
import com.project.social_media.domain.Model.Relationships;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.RelationshipsRepository;
import com.project.social_media.domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelationshipsService implements IRelationshipsService {
    private final ModelMapper modelMapper;

    private final RelationshipsRepository relationshipsRepository;
    private final UserRepository userRepository;


    @Override
    public List<Relationships> getFriends(Long userId) {
        return relationshipsRepository.findByUserIdAndStatus(userId, Relationships.RelationshipStatus.FRIENDS);
    }

    @Override
    public Relationships getRelationshipById(Long relationshipId) {
        return relationshipsRepository.findById(relationshipId).orElseThrow(() -> new RuntimeException("Relationship not found"));
    }

    @Override
    public Relationships createRelationship(Long userId1, Long userId2, Relationships.RelationshipStatus status) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (relationshipsRepository.existsByUser1UserIdAndUser2UserIdAndStatus(userId1, userId2, status)){
            throw new RuntimeException("Relationship already exists");
        }
        Relationships relationship = new Relationships(user1, user2, status, LocalDateTime.now(), LocalDateTime.now());
        return relationshipsRepository.save(relationship);
    }

    @Override
    public Relationships updateRelationship(Long relationshipId, Relationships.RelationshipStatus status) {
        Relationships relationship = getRelationshipById(relationshipId);
        relationship.updateRelationshipStatus(status, LocalDateTime.now());
        return relationshipsRepository.save(relationship);
    }

    @Override
    public void deleteRelationship(Long relationshipId) {
        Relationships relationship = getRelationshipById(relationshipId);
        relationshipsRepository.delete(relationship);
    }

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

    @Override
    public FriendshipCheck areFriends(Long userId1, Long userId2) {
        if (userId1.equals(userId2)) {
            return new FriendshipCheck(false, null); // Không thể là bạn bè với chính mình
        }

        Optional<Relationships> relationship = relationshipsRepository.findByUserIdsAndStatus(
                userId1, userId2, Relationships.RelationshipStatus.FRIENDS);

        return relationship.map(r -> new FriendshipCheck(true, r.getRelationshipId()))
                .orElse(new FriendshipCheck(false, null));
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
