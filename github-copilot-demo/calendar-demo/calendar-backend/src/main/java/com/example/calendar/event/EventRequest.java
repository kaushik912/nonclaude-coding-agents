package com.example.calendar.event;

import com.example.calendar.attendee.Attendee.Status;
import com.example.calendar.recurrence.RecurrenceRule.Frequency;
import com.example.calendar.reminder.Reminder.Method;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

public record EventRequest(
        @NotBlank String title,
        String description,
        String location,
        @NotNull Instant startTime,
        @NotNull Instant endTime,
        RecurrenceRequest recurrence,
        List<AttendeeRequest> attendees,
        List<ReminderRequest> reminders) {

    public record RecurrenceRequest(
            @NotNull Frequency frequency,
            Integer interval,
            Instant until) {
    }

    public record AttendeeRequest(
            @NotBlank String email,
            Status status) {
    }

    public record ReminderRequest(
            @NotNull Method method,
            @NotNull Integer minutesBefore) {
    }
}