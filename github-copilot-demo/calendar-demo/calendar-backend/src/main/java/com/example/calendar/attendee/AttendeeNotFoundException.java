package com.example.calendar.attendee;

public class AttendeeNotFoundException extends RuntimeException {
    public AttendeeNotFoundException(Long id) {
        super("Attendee not found: " + id);
    }
}