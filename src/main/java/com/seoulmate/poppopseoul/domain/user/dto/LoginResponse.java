package com.seoulmate.poppopseoul.domain.user.dto;

import com.seoulmate.poppopseoul.common.enumeration.LoginType;
import com.seoulmate.poppopseoul.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String email;
    private LoginType loginType;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(User user, TokenDTO tokenDTO) {
        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .loginType(user.getLoginType())
                .nickname(user.getNickname())
                .accessToken(tokenDTO.getAccessToken())
                .refreshToken(tokenDTO.getRefreshToken())
                .build();
    }
}
