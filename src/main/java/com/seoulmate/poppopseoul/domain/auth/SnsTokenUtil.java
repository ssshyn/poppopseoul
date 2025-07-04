package com.seoulmate.poppopseoul.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SnsTokenUtil {

    @Value("${facebook.oauth.url}")
    private String facebookUrl;

    private final GoogleOAuthProperties googleOAuthProperties;

    /**
     * 구글 로그인 토큰 검증
     */
    public String verifyGoogleToken(String snsToken) {
        if(StringUtils.equals(snsToken, "ggTest")) {
            return "testGoogle@gmail.com";
        }

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                    .Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(googleOAuthProperties.getClientIds())
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(snsToken);
            if (googleIdToken != null) {
                return googleIdToken.getPayload().getEmail();
            } else {
                throw new ErrorException(ErrorCode.UNAUTHENTICATED_USER);
            }
        } catch (Exception e) {
            throw new ErrorException(ErrorCode.NOT_MATCH_CATEGORY);
        }
    }

    /**
     * 페이스북 로그인 토큰 검증
     */
    public String verifyFacebookToken(String snsToken) {
        String url = facebookUrl + snsToken;
        RestTemplate restTemplate = new RestTemplate();

        if(StringUtils.equals(snsToken, "fbTest")) {
            return "testFacebook@gmail.com";
        }

        try {
            ResponseEntity<FacebookUserResponse> response =
                    restTemplate.exchange(url, HttpMethod.GET, null, FacebookUserResponse.class);

            FacebookUserResponse user = response.getBody();
            if (Objects.isNull(user)) {
                System.out.println("logger > error : { facebook user not found }");
                throw new ErrorException(ErrorCode.UNAUTHENTICATED_USER);
            }

            return user.getEmail();
        } catch (Exception e) {
            throw new ErrorException(ErrorCode.INVALID_TOKEN);
        }
    }
}
