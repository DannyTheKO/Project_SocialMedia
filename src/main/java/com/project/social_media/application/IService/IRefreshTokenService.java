package com.project.social_media.application.IService;

import com.project.social_media.domain.Model.JPA.RefreshToken;

public interface IRefreshTokenService {

    /**
     * Get create Refresh Token
     *
     * @param userId Long
     * @return Object {RefreshToken}
     */
    RefreshToken createRefreshToken(Long userId);

    /**
     * Verify Refresh Token
     *
     * @param token String
     * @return Object {RefreshToken}
     */
    RefreshToken verifyRefreshToken(String token);

    /**
     * Delete Refresh Token
     *
     * @param token String
     */
    void deleteRefreshToken(String token);
}
