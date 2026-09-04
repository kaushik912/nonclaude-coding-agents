package com.example.calendar.event;

import com.example.calendar.recurrence.RecurrenceRule.Frequency;
import java.time.Instant;

public record EventResponse(
        Long id,
        String title,
        String description,
        String location,
        Instant startTime,
        Instant endTime,
        Instant createdAt,
        Instant updatedAt,
        RecurrenceResponse recurrence) {

    public record RecurrenceResponse(
            Frequency frequency,
            Integer interval,
            Instant until) {
    }

    public static EventResponse from(Event e) {
        return new EventResponse(
                e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getLocation(),
                e.getStartTime(),
                e.getEndTime(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                null);
    }
}