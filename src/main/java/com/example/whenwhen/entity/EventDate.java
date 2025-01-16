package com.example.whenwhen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "event_dates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"event_id", "date"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
