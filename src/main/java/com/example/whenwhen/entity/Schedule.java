package com.example.whenwhen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(
        name = "schedules",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"event_id", "user_id"}),
                @UniqueConstraint(columnNames = {"event_id", "nickname"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String timeSlots;

    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Event event;
}