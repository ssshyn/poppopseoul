package com.seoulmate.poppopseoul.domain.user.controller;

import com.seoulmate.poppopseoul.domain.user.dto.LoginRequest;
import com.seoulmate.poppopseoul.domain.user.dto.LoginResponse;
import com.seoulmate.poppopseoul.domain.user.dto.TokenDTO;
import com.seoulmate.poppopseoul.domain.user.entity.User;
import com.seoulmate.poppopseoul.domain.user.service.AuthService;
import com.seoulmate.poppopseoul.domain.user.service.UserService;
import com.seoulmate.poppopseoul.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 controller", description = "인증 API 목록")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "403", description = "BAD REQUEST", content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(name = "A0010", description = "만료된 엑세스 토큰입니다.",
                                value = """
                                        {"code": "A0010", "message": "만료된 엑세스 토큰입니다."}
                                        """)
                }, schema = @Schema(implementation = ErrorResponse.class)
        )),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(name = "500", description = "INTERNAL SERVER ERROR",
                                value = """
                                        {"status": 500, "message": "INTERNAL SERVER ERROR"}
                                        """)
                }, schema = @Schema(implementation = ErrorResponse.class)
        ))
})
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
