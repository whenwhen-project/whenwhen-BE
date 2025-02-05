package com.example.whenwhen.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long accessTokenValidity;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 액세스 토큰 생성
    public String generateAccessToken(Long userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(secretKey) // 서명
                .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    // 토큰 검증 및 사용자 ID 추출
    public Long validateTokenAndExtractUserId(String token) {
        try {
            String sub = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Long.parseLong(sub);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.", e);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("유효하지 않은 서명입니다.", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("토큰 검증 중 오류가 발생했습니다.", e);
        }
    }
}
