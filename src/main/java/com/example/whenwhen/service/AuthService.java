package com.example.whenwhen.service;

import com.example.whenwhen.dto.LoginResponseDto;
import com.example.whenwhen.entity.RefreshToken;
import com.example.whenwhen.entity.User;
import com.example.whenwhen.repository.RefreshTokenRepository;
import com.example.whenwhen.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoAuthProvider kakaoAuthProvider;

    /**
     * 카카오 로그인, 회원가입을 처리
     * @param code 클라이언트로부터 받은 카카오 인가 코드
     * @return {@link LoginResponseDto} 반환
     */
    @Transactional
    public LoginResponseDto handleKakaoCallback(String code) {
        // 1. 카카오 액세스 토큰 획득
        Map<String, Object> kakaoAccessToken = kakaoAuthProvider.getKakaoToken(code);
        String idToken = (String) kakaoAccessToken.get("id_token");

        // 2. 카카오 액세스 토큰 내 ID token에서 사용자 정보를 decode
        Map<String, Object> userInfo = kakaoAuthProvider.decodeIdTokenToUserInfo(idToken);

        String sub = (String) userInfo.get("sub");
        String accountName = (String) userInfo.get("nickname");
        String thumbnailImageUrl = (String) userInfo.get("profile_image_url");

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
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken();

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
