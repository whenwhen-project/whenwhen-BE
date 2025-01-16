package com.example.whenwhen.service;

import com.example.whenwhen.dto.ScheduleRequestDto;
import com.example.whenwhen.dto.ScheduleResponseDto;
import com.example.whenwhen.entity.Event;
import com.example.whenwhen.entity.Schedule;
import com.example.whenwhen.entity.User;
import com.example.whenwhen.repository.EventDateRepository;
import com.example.whenwhen.repository.EventRepository;
import com.example.whenwhen.repository.ScheduleRepository;
import com.example.whenwhen.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventDateRepository eventDateRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        User user;

        if (scheduleRequestDto.getUserId() == null) {
            user = userRepository.findByKakaoSub("0")
                    .orElseThrow(() -> new EntityNotFoundException("Default user not found"));
        } else {
            user = userRepository.findById(scheduleRequestDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + scheduleRequestDto.getUserId()));
        }

        Event event = eventRepository.findById(scheduleRequestDto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + scheduleRequestDto.getEventId()));

        // 타임슬롯 확인 로직, 저장, 리턴 로직
        int eventTimeSlotLength = eventDateRepository.getAllByEvent(event).size() * (event.getEndHour() - event.getStartHour()) * 2;
        if (eventTimeSlotLength != scheduleRequestDto.getTimeSlots().length()) {
            throw new RuntimeException("Requested timeSlot=" + scheduleRequestDto.getTimeSlots().length() +
                    ", Event timeSlot=" + eventTimeSlotLength);
        }

        Schedule schedule = Schedule.builder()
                .nickname(scheduleRequestDto.getNickname())
                .timeSlots(scheduleRequestDto.getTimeSlots())
                .user(user)
                .event(event)
                .build();
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.builder()
                .scheduleId(savedSchedule.getId())
                .eventId(event.getId())
                .userId(user.getId())
                .nickname(savedSchedule.getNickname())
                .timeSlots(savedSchedule.getTimeSlots())
                .build();
    }
}
