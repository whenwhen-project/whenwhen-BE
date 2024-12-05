package com.example.whenwhen.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EventRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 0, message = "Start hour must be at least 0")
    @Max(value = 23, message = "Start hour must be at most 23")
    @NotNull(message = "Start hour is required")
    private int startHour;

    @Min(value = 0, message = "End hour must be at least 0")
    @Max(value = 23, message = "End hour must be at most 23")
    @NotNull(message = "End hour is required")
    private int endHour;
}