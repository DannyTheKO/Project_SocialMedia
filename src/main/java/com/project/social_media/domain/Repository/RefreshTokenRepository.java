package com.project.social_media.domain.Repository;

import com.project.social_media.domain.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
        Optional<RefreshToken> findByToken(String token);

        void deleteByUserUserId(Long userId);
}
