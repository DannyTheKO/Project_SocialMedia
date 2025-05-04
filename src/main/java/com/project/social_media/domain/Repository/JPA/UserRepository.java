package com.project.social_media.domain.Repository.JPA;

import com.project.social_media.domain.Model.JPA.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserId(Long userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
