package com.seoulmate.poppopseoul.domain.user.dto;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.common.enumeration.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Schema(description = "SNS 로그인 토큰", example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaXNzIjoiYWNjb3VudHMuZ29vZ2xlLmNvbSIsImF1ZCI6InlvdXItY2xpZW50LWlkIiwiZXhwIjo5OTk5OTk5OTk5fQ.dummy-signature")
    private String snsToken;
    @Schema(description = "로그인 타입")
    private LoginType loginType;
    @Schema(description = "언어")
    private LanguageCode languageCode;
}
