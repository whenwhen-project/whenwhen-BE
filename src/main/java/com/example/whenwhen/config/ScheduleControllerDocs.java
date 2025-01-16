package com.example.whenwhen.config;

import com.example.whenwhen.dto.ScheduleRequestDto;
import com.example.whenwhen.dto.ScheduleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "[Schedule API]")
public interface ScheduleControllerDocs {
    @Operation(
            summary = "새 스케쥴 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "스케쥴 생성 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    ResponseEntity<?> createSchedule(ScheduleRequestDto scheduleRequestDto);
}
