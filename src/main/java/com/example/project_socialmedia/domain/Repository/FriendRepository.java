package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
