package com.example.whenwhen.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "[Auth API]")
public interface AuthControllerDocs {
    @Operation(
            summary = "카카오 로그인 URL 리다이렉트",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = "카카오 로그인 URL로 302 Redirect",
                            headers = {
                                    @Header(name = "Location", schema = @Schema(type = "string"))
                            }
                    )
            }
    )
    public ResponseEntity<?> redirectKakaoLoginUrl();

    @Operation(
            summary = "카카오로 로그인 혹은 회원가입",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인/회원가입 성공",
                            useReturnTypeSchema = true
                    ),
            }
    )
    public ResponseEntity<?> handleKakaoCallback(@RequestParam String code);
}
