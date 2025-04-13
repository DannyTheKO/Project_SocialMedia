package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
