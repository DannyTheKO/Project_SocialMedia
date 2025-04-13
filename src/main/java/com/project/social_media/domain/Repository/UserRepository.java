package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserId(Long userId);

    User findUserByEmail(String email);

    User findUserByUsername(String username);
}
