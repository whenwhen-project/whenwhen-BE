package com.example.whenwhen.repository;

import com.example.whenwhen.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByCode(String code); // 이벤트 코드로 이벤트를 조회
}