package com.example.whenwhen.controller;

import com.example.whenwhen.dto.ApiResponseWrapper;
import com.example.whenwhen.dto.EventRequestDto;
import com.example.whenwhen.dto.EventResponseDto;
import com.example.whenwhen.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@Tag(name = "[Event API]")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // POST /events
    @PostMapping
    @Operation(
            summary = "Create a new event",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "이벤트 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponseWrapper.class,
                                            subTypes = {EventResponseDto.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "유효성 검증 실패",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                                "success": false,
                                                "error": {
                                                    "code": "VALIDATION_ERROR",
                                                    "message": "Invalid input data"
                                                }
                                            }
                                            """))
                    )
            }
    )
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventRequestDto requestDto) {
        EventResponseDto eventResponseDto = eventService.createEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<EventResponseDto>builder()
                        .success(true)
                        .message("Event created successfully.")
                        .data(eventResponseDto)
                        .build()
        );
    }

    // TODO: wrapper 클래스 내의 data도 보이게 하기
    // GET /events/{eventCode}
    @GetMapping("/{eventCode}")
    @Operation(
            summary = "Retrieve event by code",
            description = "이벤트 코드를 사용하여 이벤트 세부 정보를 검색합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "이벤트 세부 정보 검색 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponseWrapper.class,
                                            subTypes = {EventResponseDto.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "이벤트를 찾을 수 없음",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                              "success": false,
                                              "error": {
                                                "code": "EVENT_CODE_NOT_EXISTS",
                                                "message": "존재하지 않는 이벤트 코드입니다."
                                              }
                                            }
                                            """))
                    )
            }
    )
    public ResponseEntity<?> getEventByCode(@PathVariable String eventCode) {
        try {
            EventResponseDto eventResponseDto = eventService.getEventByCode(eventCode);
            return ResponseEntity.ok().body(
                    ApiResponseWrapper.<EventResponseDto>builder()
                            .success(true)
                            .message("Event retrieved successfully.")
                            .data(eventResponseDto)
                            .build()
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponseWrapper.builder()
                            .success(false)
                            .message(e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }
}
