package com.example.whenwhen.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventResponseDto {
    private String title;
    private String code;
    private int startHour; // 24시간 형식
    private int endHour;   // 24시간 형식
    private String status;
}
