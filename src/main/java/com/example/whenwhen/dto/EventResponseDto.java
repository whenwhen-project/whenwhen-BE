package com.example.whenwhen.dto;

import lombok.Builder;

@Builder
public class EventResponseDto {
    private String title;
    private String code;
    private int startHour;
    private int endHour;
    private String status;
}
