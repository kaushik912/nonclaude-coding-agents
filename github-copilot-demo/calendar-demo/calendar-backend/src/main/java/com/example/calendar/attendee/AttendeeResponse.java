package com.example.calendar.attendee;

import com.example.calendar.attendee.Attendee.Status;

public record AttendeeResponse(
        Long id,
        String email,
        Status status) {

    public static AttendeeResponse from(Attendee a) {
        return new AttendeeResponse(a.getId(), a.getEmail(), a.getStatus());
    }
}