package com.example.whenwhen.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "API 공통 응답 형식")
public class ApiResponseWrapper<T> {

    @Schema(description = "요청 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "Event created successfully.")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;
}
