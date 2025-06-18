package com.seoulmate.poppopseoul.domain.user.service;


import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.common.enumeration.LoginType;
import com.seoulmate.poppopseoul.common.enumeration.NicknamePrefix;
import com.seoulmate.poppopseoul.common.enumeration.NicknameSuffix;
import com.seoulmate.poppopseoul.domain.auth.SnsTokenUtil;
import com.seoulmate.poppopseoul.domain.user.dto.LoginRequest;
import com.seoulmate.poppopseoul.domain.user.entity.User;
import com.seoulmate.poppopseoul.domain.user.repository.UserRepository;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final SnsTokenUtil snsTokenUtil;
    private final UserRepository userRepository;

    /**
     * 로그인 / 회원가입
     */
    @Transactional
    public User tryRegistrationAndReturnUser(LoginRequest condition) {

        // SNS 로그인 채널 별 token 검증 처리
        String userEmail = Optional.of(verifySnsLogin(condition)).orElseThrow(() -> new ErrorException(ErrorCode.UNAUTHENTICATED_USER));

        // 있으면 로그인, 없으면 회원가입
        return userRepository.findByEmailAndLoginType(userEmail, condition.getLoginType())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(userEmail);
                    newUser.setLoginType(condition.getLoginType());
                    newUser.setNickname(makeNickname(condition.getLanguageCode()));
                    return userRepository.save(newUser);
                });
    }

    /**
     * SNS Login > 유저 이메일 받아오기
     */
    private String verifySnsLogin(LoginRequest condition) {
        LoginType loginType = condition.getLoginType();
        String snsToken = StringUtils.trimToEmpty(condition.getSnsToken());
        String email = "";

        switch (loginType) {
            case FACEBOOK -> email = snsTokenUtil.verifyFacebookToken(snsToken);
            case GOOGLE -> email = snsTokenUtil.verifyGoogleToken(snsToken);
        }

        return email;
    }

    /**
     * 초기 닉네임 생성
     */
    private String makeNickname(LanguageCode languageCode) {
        String nickname = "";
        boolean isPresent;

        do {
            NicknamePrefix prefix = getRandomEnumValue(NicknamePrefix.class);
            NicknameSuffix suffix = getRandomEnumValue(NicknameSuffix.class);

            nickname = languageCode.isKorean()
                    ? prefix.getKor() + " " + suffix.getKor()
                    : prefix.getEng() + " " + suffix.getEng();

            isPresent = userRepository.findByNickname(nickname).isPresent();
        } while (isPresent);

        return nickname;
    }

    private static <T extends Enum<?>> T getRandomEnumValue(Class<T> clazz) {
        T[] enumConstants = clazz.getEnumConstants();
        return enumConstants[ThreadLocalRandom.current().nextInt(enumConstants.length)];
    }
}
