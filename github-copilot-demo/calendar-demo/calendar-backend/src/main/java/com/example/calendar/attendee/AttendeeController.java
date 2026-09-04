package com.example.calendar.attendee;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events/{eventId}/attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttendeeResponse add(@PathVariable Long eventId, @Valid @RequestBody AttendeeRequest request) {
        return attendeeService.add(eventId, request);
    }

    @GetMapping
    public List<AttendeeResponse> list(@PathVariable Long eventId) {
        return attendeeService.list(eventId);
    }

    @PatchMapping("/{attendeeId}")
    public AttendeeResponse updateStatus(@PathVariable Long eventId,
                                         @PathVariable Long attendeeId,
                                         @Valid @RequestBody AttendeeStatusRequest request) {
        return attendeeService.updateStatus(eventId, attendeeId, request);
    }
}