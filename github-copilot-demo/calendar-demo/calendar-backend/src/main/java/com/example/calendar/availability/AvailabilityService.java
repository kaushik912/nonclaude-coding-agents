package com.example.calendar.availability;

import com.example.calendar.event.EventRepository;
import com.example.calendar.event.EventResponse;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvailabilityService {

    private final EventRepository eventRepository;

    public AvailabilityService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<EventResponse> findConflicts(Instant from, Instant to) {
        return eventRepository.findOverlapping(from, to).stream()
                .map(e -> EventResponse.from(e, List.of()))
                .toList();
    }
}
