package com.seoulmate.poppopseoul.domain.user.dto;

import com.seoulmate.poppopseoul.common.enumeration.LoginType;
import com.seoulmate.poppopseoul.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private Long id;
    private String email;
    private LoginType loginType;
    private String nickname;

    public static LoginInfo of(User user) {
        return LoginInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .loginType(user.getLoginType())
                .nickname(user.getNickname())
                .build();
    }
}
