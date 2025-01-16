package com.example.whenwhen.controller;

import com.example.whenwhen.config.ScheduleControllerDocs;
import com.example.whenwhen.dto.ApiResponseWrapper;
import com.example.whenwhen.dto.ScheduleRequestDto;
import com.example.whenwhen.dto.ScheduleResponseDto;
import com.example.whenwhen.entity.Schedule;
import com.example.whenwhen.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController implements ScheduleControllerDocs {
    private final ScheduleService scheduleService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<ScheduleResponseDto>> createSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponseWrapper.<ScheduleResponseDto>builder()
                    .success(true)
                    .message("Schedule created successfully.")
                    .data(scheduleResponseDto)
                    .build()
        );
    }

}
