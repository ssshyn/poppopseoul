package com.seoulmate.poppopseoul.domain.user.service;

import com.seoulmate.poppopseoul.config.jwt.JWTUtil;
import com.seoulmate.poppopseoul.domain.user.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final JWTUtil jwtUtil;

    @Value("${spring.jwt.refreshTokenExpiration}")
    private Long refreshTokenExpiredMs;

    @Transactional
    public TokenDTO generateAndSaveNewToken(Long userId) {
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken();

        Long expirationSecond = refreshTokenExpiredMs / 1000;

        return new TokenDTO(accessToken, refreshToken);
    }

}
