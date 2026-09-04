package com.example.calendar.attendee;

import com.example.calendar.attendee.Attendee.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AttendeeRequest(
        @Email @NotNull String email,
        Status status) {
}