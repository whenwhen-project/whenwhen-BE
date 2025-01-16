package com.example.whenwhen.dto;

import com.example.whenwhen.enums.EventStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Schema(description = "이벤트 응답 DTO")
public class EventResponseDto {
    private Long eventId;
    private String title;
    private String code;
    private int startHour;
    private int endHour;
    private List<LocalDate> dates;
    private EventStatus status;
}
