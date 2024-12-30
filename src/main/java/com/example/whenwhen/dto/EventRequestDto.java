package com.example.whenwhen.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EventRequestDto {
    @NotBlank
    private String title;

    @NotNull
    @Min(value = 0)
    @Max(value = 23)
    private int startHour;

    @NotNull
    @Min(value = 0)
    @Max(value = 23)
    private int endHour;
}