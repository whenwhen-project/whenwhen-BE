package com.example.whenwhen.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Schema(description = "이벤트 응답 DTO")
public class EventResponseDto {
    private String title;
    private String code;
    private int startHour;
    private int endHour;
    private String status;
}
