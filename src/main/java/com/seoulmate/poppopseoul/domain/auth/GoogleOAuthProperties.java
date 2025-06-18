package com.seoulmate.poppopseoul.domain.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "google.oauth")
public class GoogleOAuthProperties {
    private List<String> clientIds;
}
