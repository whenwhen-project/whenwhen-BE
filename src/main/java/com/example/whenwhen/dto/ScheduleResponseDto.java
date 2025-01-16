package com.example.whenwhen.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "스케쥴 응답 DTO")
public class ScheduleResponseDto {
    private Long scheduleId;
    private Long eventId;
    private Long userId;
    private String nickname;
    private String timeSlots;
}
