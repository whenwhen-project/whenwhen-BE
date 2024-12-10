package com.example.whenwhen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 기본 키

    @Column(nullable = false)
    private String title;  // 이벤트 제목

    @Column(unique = true, nullable = false)
    private String code;  // 초대 코드

    @Column(name = "start_hour", nullable = false)
    private int startHour;  // 일별 시작 시간

    @Column(name = "end_hour", nullable = false)
    private int endHour;  // 일별 종료 시간


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE; // 이벤트 상태

    public enum Status {
        ACTIVE, COMPLETED
    }

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성 시간
}