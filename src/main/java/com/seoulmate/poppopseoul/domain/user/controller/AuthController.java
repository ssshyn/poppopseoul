package com.seoulmate.poppopseoul.domain.user.controller;

import com.seoulmate.poppopseoul.domain.user.dto.LoginRequest;
import com.seoulmate.poppopseoul.domain.user.dto.LoginResponse;
import com.seoulmate.poppopseoul.domain.user.dto.TokenDTO;
import com.seoulmate.poppopseoul.domain.user.entity.User;
import com.seoulmate.poppopseoul.domain.user.service.AuthService;
import com.seoulmate.poppopseoul.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 로그인 및 회원등록
        User user = userService.tryRegistrationAndReturnUser(loginRequest);
        // 토큰 생성
        TokenDTO tokenDTO = authService.generateAndSaveNewToken(user.getId());

        return ResponseEntity.ok(LoginResponse.of(user, tokenDTO));
    }
}
