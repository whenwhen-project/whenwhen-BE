package com.example.whenwhen.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ScheduleRequestDto {
    @NotNull
    private Long eventId;

    private Long userId;

    @NotBlank
    private String nickname;

    @Pattern(regexp = "^[01]+$")
    @NotBlank
    private String timeSlots;
}
