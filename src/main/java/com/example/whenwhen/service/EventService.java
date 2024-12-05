package com.example.whenwhen.service;

import com.example.whenwhen.dto.EventRequestDto;
import com.example.whenwhen.dto.EventResponseDto;
import com.example.whenwhen.entity.Event;
import com.example.whenwhen.exception.EventNotFoundException;
import com.example.whenwhen.repository.EventRepository;
import com.example.whenwhen.util.CodeGenerator;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // 이벤트 생성
    public EventResponseDto createEvent(EventRequestDto requestDto) {
        // 랜덤 코드 생성
        String randomCode;
        do {
            randomCode = CodeGenerator.generateRandomCode();
        } while (eventRepository.findByCode(randomCode).isPresent()); // 중복 검사

        Event event = Event.builder()
                .title(requestDto.getTitle())
                .code(randomCode)
                .startHour(requestDto.getStartHour())
                .endHour(requestDto.getEndHour())
                .build();

        Event savedEvent = eventRepository.save(event);

        return EventResponseDto.builder()
                .title(savedEvent.getTitle())
                .code(savedEvent.getCode())
                .startHour(savedEvent.getStartHour())
                .endHour(savedEvent.getEndHour())
                .status(savedEvent.getStatus().name())
                .build();
    }

    // 코드로 이벤트 조회
    public EventResponseDto getEventByCode(String code) {
        Event event = eventRepository.findByCode(code)
                .orElseThrow(() -> new EventNotFoundException("Event not found with code: " + code));

        return EventResponseDto.builder()
                .title(event.getTitle())
                .code(event.getCode())
                .startHour(event.getStartHour())
                .endHour(event.getEndHour())
                .status(event.getStatus().name())
                .build();
    }
}
