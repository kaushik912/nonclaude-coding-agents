package com.example.calendar.attendee;

import com.example.calendar.attendee.Attendee.Status;
import com.example.calendar.event.Event;
import com.example.calendar.event.EventNotFoundException;
import com.example.calendar.event.EventRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;

    public AttendeeService(AttendeeRepository attendeeRepository, EventRepository eventRepository) {
        this.attendeeRepository = attendeeRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public AttendeeResponse add(Long eventId, AttendeeRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        Attendee attendee = new Attendee();
        attendee.setEvent(event);
        attendee.setEmail(request.email());
        attendee.setStatus(request.status() == null ? Status.PENDING : request.status());
        return AttendeeResponse.from(attendeeRepository.save(attendee));
    }

    @Transactional
    public AttendeeResponse updateStatus(Long eventId, Long attendeeId, AttendeeStatusRequest request) {
        Attendee attendee = attendeeRepository.findById(attendeeId)
                .filter(a -> a.getEvent().getId().equals(eventId))
                .orElseThrow(() -> new AttendeeNotFoundException(attendeeId));
        attendee.setStatus(request.status());
        return AttendeeResponse.from(attendeeRepository.save(attendee));
    }

    @Transactional(readOnly = true)
    public List<AttendeeResponse> list(Long eventId) {
        return attendeeRepository.findByEventId(eventId).stream().map(AttendeeResponse::from).toList();
    }
}