package com.example.whenwhen.repository;

import com.example.whenwhen.entity.Event;
import com.example.whenwhen.entity.EventDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDateRepository extends JpaRepository<EventDate, Long> {
    List<EventDate> getAllByEvent(Event event);
}
