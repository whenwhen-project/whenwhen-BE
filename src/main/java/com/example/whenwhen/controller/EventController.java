package com.example.whenwhen.controller;

import com.example.whenwhen.config.EventControllerDocs;
import com.example.whenwhen.dto.ApiResponseWrapper;
import com.example.whenwhen.dto.EventRequestDto;
import com.example.whenwhen.dto.EventResponseDto;
import com.example.whenwhen.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController implements EventControllerDocs {

    private final EventService eventService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<EventResponseDto>> createEvent(@RequestBody @Valid EventRequestDto requestDto) {
        EventResponseDto eventResponseDto = eventService.createEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<EventResponseDto>builder()
                        .success(true)
                        .message("Event created successfully.")
                        .data(eventResponseDto)
                        .build()
        );
    }

    @Override
    @GetMapping("/{eventCode}")
    public ResponseEntity<ApiResponseWrapper<EventResponseDto>> getEventByCode(@PathVariable String eventCode) {
        EventResponseDto eventResponseDto = eventService.getEventByCode(eventCode);
        return ResponseEntity.ok().body(
                ApiResponseWrapper.<EventResponseDto>builder()
                        .success(true)
                        .message("Event retrieved successfully.")
                        .data(eventResponseDto)
                        .build()
        );
    }
}