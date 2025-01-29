package com.example.whenwhen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoAuthProvider {

    private final WebClient webClient;

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

    // TODO: Map Warning 수정하기

    /**
     * 카카오로부터 액세스 토큰과 ID 토큰 등 획득
     * @param code 카카오 OAuth에서 받은 인가 코드
     * @return 액세스 토큰, ID 토큰 등
     */
    public Map<String, Object> getKakaoToken(String code) {
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue("grant_type=authorization_code"
                        + "&client_id=" + clientId
                        + "&redirect_uri=" + baseUrl + subUrl
                        + "&code=" + code)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    /**
     * 카카오 액세스 토큰에 있는 ID 토큰을 디코딩하고 사용자 정보 반환
     * @param idToken 카카오 ID 토큰
     * @return Map 형식의 사용자 정보. sub, nickname, picture 등
     */
    public Map<String, Object> decodeIdTokenToUserInfo(String idToken) {
        try {
            String[] parts = idToken.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payload, Map.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ID token", e);
        }
    }
}
