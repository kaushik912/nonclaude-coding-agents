package com.example.calendar.event;

import com.example.calendar.recurrence.RecurrenceRule.Frequency;
import com.example.calendar.reminder.Reminder;
import java.time.Instant;
import java.util.List;

public record EventResponse(
        Long id,
        String title,
        String description,
        String location,
        Instant startTime,
        Instant endTime,
        Instant createdAt,
        Instant updatedAt,
        RecurrenceResponse recurrence,
        List<ReminderResponse> reminders) {

    public record RecurrenceResponse(
            Frequency frequency,
            Integer interval,
            Instant until) {
    }

    public record ReminderResponse(
            Long id,
            Reminder.Method method,
            Integer minutesBefore) {

        public static ReminderResponse from(Reminder r) {
            return new ReminderResponse(r.getId(), r.getMethod(), r.getMinutesBefore());
        }
    }

    public static EventResponse from(Event e, List<Reminder> reminders) {
        return new EventResponse(
                e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getLocation(),
                e.getStartTime(),
                e.getEndTime(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                null,
                reminders.stream().map(ReminderResponse::from).toList());
    }
}