package com.project.social_media.application.Service;


import com.project.social_media.application.IService.IRefreshTokenService;
import com.project.social_media.domain.Model.RefreshToken;
import com.project.social_media.domain.Model.User;
import com.project.social_media.domain.Repository.RefreshTokenRepository;
import com.project.social_media.domain.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    // Refresh token expired time: 30 days
    private final Long REFRESH_TOKEN_VALIDITY = 30 * 24 * 60 * 60 * 1000L;

    @Transactional
    @Override
    public RefreshToken createRefreshToken(Long userId) {
        // Delete old user's refresh token (if exist)
        refreshTokenRepository.deleteByUserUserId(userId);

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found !"));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(REFRESH_TOKEN_VALIDITY/1000));
        refreshToken.setCreatedAt(LocalDateTime.now());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token).orElseThrow(()-> new RuntimeException("Token not found !"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    @Override
    public void deleteRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.
                findByToken(token).orElseThrow(()-> new RuntimeException("Token not found !"));

        refreshTokenRepository.delete(refreshToken);
    }
}
