package com.seoulmate.poppopseoul.common.util;

import com.seoulmate.poppopseoul.domain.user.dto.LoginInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUtil {
    private static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * Retrieves the LoginInfo from the current security context.
     *
     * @return LoginInfo object if user is authenticated, null otherwise
     */
    private static LoginInfo getLoginInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getPrincipal() == null) {
                return null;
            }

            String principal = authentication.getPrincipal().toString();
            if (StringUtils.equals(principal, ANONYMOUS_USER)) {
                return null;
            }

            return (LoginInfo) authentication.getPrincipal();
        } catch (Exception e) {
            // Log the exception if needed
            return null;
        }
    }

    /**
     * 유저 객체
     */
    public static Optional<LoginInfo> getUser() {
        return Optional.ofNullable(getLoginInfo());
    }

    /**
     * 유저 아이디 (아이디)
     */
    public static Long getUserId() {
        LoginInfo user = getLoginInfo();
        return user != null ? user.getId() : null;
    }

    /**
     * 유저 아이디 (이메일)
     */
    public static String getUserEmail() {
        LoginInfo user = getLoginInfo();
        return user != null ? user.getEmail() : null;
    }

    /**
     * 유저 닉네임
     */
    public static String getNickname() {
        LoginInfo user = getLoginInfo();
        return user != null ? user.getNickname() : null;
    }

    /**
     * Checks if a user is currently authenticated.
     *
     * @return true if user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        return getLoginInfo() != null;
    }
}
