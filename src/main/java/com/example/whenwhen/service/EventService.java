package com.example.whenwhen.service;

import com.example.whenwhen.dto.EventRequestDto;
import com.example.whenwhen.dto.EventResponseDto;
import com.example.whenwhen.entity.Event;
import com.example.whenwhen.entity.EventDate;
import com.example.whenwhen.repository.EventDateRepository;
import com.example.whenwhen.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventDateRepository eventDateRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8; // 코드 길이
    private static final Random RANDOM = new SecureRandom();

    // Event, EventDate 생성
    @Transactional
    public EventResponseDto createEvent(EventRequestDto requestDto) {
        String randomCode;
        do {
            randomCode = generateRandomCode();
        } while (eventRepository.existsByCode(randomCode)); // 중복 검사

        Event event = Event.builder()
                .title(requestDto.getTitle())
                .code(randomCode)
                .startHour(requestDto.getStartHour())
                .endHour(requestDto.getEndHour())
                .build();
        Event savedEvent = eventRepository.save(event);

        List<EventDate> savedEventDates = requestDto.getDates().stream()
                .map(localDate -> EventDate.builder().date(localDate).event(event).build())
                .map(eventDateRepository::save)
                .toList();

        return EventResponseDto.builder()
                .eventId(event.getId())
                .title(savedEvent.getTitle())
                .code(savedEvent.getCode())
                .startHour(savedEvent.getStartHour())
                .endHour(savedEvent.getEndHour())
                .dates(savedEventDates.stream().map(EventDate::getDate).toList())
                .status(savedEvent.getStatus())
                .build();
    }

    // 코드로 이벤트 조회
    public EventResponseDto getEventByCode(String code) {
        Event event = eventRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with code: " + code));

        return EventResponseDto.builder()
                .eventId(event.getId())
                .title(event.getTitle())
                .code(event.getCode())
                .startHour(event.getStartHour())
                .endHour(event.getEndHour())
                .dates(eventDateRepository.getAllByEvent(event).stream().map(EventDate::getDate).toList())
                .status(event.getStatus())
                .build();
    }

    private String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
