package com.example.calendar.attendee;

import com.example.calendar.attendee.Attendee.Status;
import jakarta.validation.constraints.NotNull;

public record AttendeeStatusRequest(@NotNull Status status) {
}