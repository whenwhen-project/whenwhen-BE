package com.example.whenwhen.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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

    @NotNull
    @Size(min = 1)
    private List<LocalDate> dates;
}