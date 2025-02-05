package com.example.whenwhen.service;

import com.example.whenwhen.dto.KakaoTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoAuthProvider {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${auth.kakao.client-id}")
    private String clientId;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${auth.kakao.sub-url}")
    private String subUrl;

    @Value("${auth.kakao.scope}")
    private String scope;

    public String getKakaoLoginRedirectUrl() {
        return "https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + baseUrl + subUrl
                + "&scope=" + scope;
    }

    /**
     * 카카오로부터 액세스 토큰과 ID 토큰 등 획득
     * @param code 카카오 OAuth 에서 받은 인가 코드
     * @return {@link KakaoTokenResponse}
     */
    public KakaoTokenResponse getKakaoToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        String body = "grant_type=authorization_code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + baseUrl + subUrl
                + "&code=" + code;

        return restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                KakaoTokenResponse.class
        ).getBody();
    }
}
