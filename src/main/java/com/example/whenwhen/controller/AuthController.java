package com.example.whenwhen.controller;

import com.example.whenwhen.config.AuthControllerDocs;
import com.example.whenwhen.dto.ApiResponseWrapper;
import com.example.whenwhen.dto.LoginResponseDto;
import com.example.whenwhen.service.AuthService;
import com.example.whenwhen.service.KakaoAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;
    private final KakaoAuthProvider kakaoAuthProvider;

    @GetMapping("/kakao")
    public ResponseEntity<Void> redirectKakaoLoginUrl() {
        String redirectUrl = kakaoAuthProvider.getKakaoLoginRedirectUrl();

        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<ApiResponseWrapper<LoginResponseDto>> handleKakaoCallback(@RequestParam String code) {
        LoginResponseDto loginResponseDto = authService.handleKakaoCallback(code);
        return ResponseEntity.ok().body(
                ApiResponseWrapper.<LoginResponseDto>builder()
                        .success(true)
                        .message("Login successful.")
                        .data(loginResponseDto)
                        .build()
        );
    }
}