package com.seoulmate.poppopseoul.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoulmate.poppopseoul.domain.user.dto.UserDto;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import com.seoulmate.poppopseoul.exception.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new ErrorException(ErrorCode.INVALID_TOKEN);
            }

            String accessToken = authorization.split(" ")[1];

            validationAccessToken(accessToken);

            Long userId = jwtUtil.getUserId(accessToken);

            UserDto userDto = new UserDto(userId);

            Authentication authentication = new JWTTokenAuthentication(accessToken, userDto);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ErrorException e) {
            handleErrorResponse(response, e);
        }
    }

    private void validationAccessToken(String accessToken) {
        if (jwtUtil.isExpired(accessToken)) {
            throw new ErrorException(ErrorCode.TOKEN_EXPIRED);
        } else if (!jwtUtil.isValidToken(accessToken)) {
            throw new ErrorException(ErrorCode.INVALID_TOKEN);
        }
    }

    private void handleErrorResponse(HttpServletResponse response, ErrorException e) throws IOException {
        ErrorCode errorCode = e.getErrorCode();

        response.setStatus(errorCode.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String body = objectMapper.writeValueAsString(ErrorResponse.of(errorCode));
        response.getWriter().write(body);
    }

}
