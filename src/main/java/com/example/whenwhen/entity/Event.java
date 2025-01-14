package com.example.whenwhen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // PK

    @Column(nullable = false)
    private String title;  // 이벤트 제목

    @Column(unique = true, nullable = false)
    private String code;  // 초대 코드

    @Column(nullable = false)
    private int startHour;  // 일별 시작 시간

    @Column(nullable = false)
    private int endHour;  // 일별 종료 시간


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // 이벤트 상태

    public enum Status {
        ACTIVE, COMPLETED
    }

    @Column(updatable = false)
    private Instant createdAt;  // 생성 시간

    @PrePersist
    public void onCreate() {
        if (this.status == null) {
            this.status = Status.ACTIVE;
        }
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventDate> eventDates = new ArrayList<>();
}