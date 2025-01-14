package com.example.whenwhen.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String accountName;
    private String thumbnailImageUrl;
}
