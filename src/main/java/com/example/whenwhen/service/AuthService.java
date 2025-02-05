package com.example.whenwhen.service;

import com.example.whenwhen.dto.KakaoTokenResponse;
import com.example.whenwhen.dto.LoginResponseDto;
import com.example.whenwhen.entity.RefreshToken;
import com.example.whenwhen.entity.User;
import com.example.whenwhen.repository.RefreshTokenRepository;
import com.example.whenwhen.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoAuthProvider kakaoAuthProvider;

    /**
     * 카카오 로그인, 회원가입을 처리
     * @param code 클라이언트로부터 받은 카카오 인가 코드
     * @return {@link LoginResponseDto}
     */
    @Transactional
    public LoginResponseDto handleKakaoCallback(String code) {
        // 1. 카카오 토큰 획득
        KakaoTokenResponse kakaoToken = kakaoAuthProvider.getKakaoToken(code);
        String idToken = kakaoToken.getId_token();

        // 2. 카카오 ID token에서 사용자 정보를 decode
        Claims userInfo = Jwts.parser().build().parseUnsecuredClaims(idToken).getPayload();
        String sub = userInfo.get("sub", String.class);
        String accountName = userInfo.get("nickname", String.class);
        String thumbnailImageUrl = userInfo.get("picture", String.class);

        // 3. 기존 사용자 조회 또는 신규 사용자 생성
        User user = userRepository.findByKakaoSub(sub)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .kakaoSub(sub)
                                .accountName(accountName)
                                .thumbnailImageUrl(thumbnailImageUrl)
                                .build()
                ));

        // 4. 애플리케이션 액세스/리프레시 토큰 생성 및 저장
        String accessToken = jwtProvider.generateAccessToken(user.getId());
        String refreshToken = jwtProvider.generateRefreshToken();

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .refreshToken(refreshToken)
                        .user(user)
                        .build()
        );

        // 5. LoginResponseDto 반환
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .accountName(accountName)
                .thumbnailImageUrl(thumbnailImageUrl)
                .build();
    }
}
