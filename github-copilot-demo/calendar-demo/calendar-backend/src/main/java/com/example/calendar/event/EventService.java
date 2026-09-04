package com.example.calendar.event;

import com.example.calendar.attendee.Attendee;
import com.example.calendar.attendee.Attendee.Status;
import com.example.calendar.attendee.AttendeeRepository;
import com.example.calendar.recurrence.RecurrenceRule;
import com.example.calendar.recurrence.RecurrenceRuleRepository;
import com.example.calendar.recurrence.RecurrenceService;
import com.example.calendar.recurrence.RecurrenceService.Occurrence;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RecurrenceRuleRepository recurrenceRuleRepository;
    private final RecurrenceService recurrenceService;
    private final AttendeeRepository attendeeRepository;

    public EventService(EventRepository eventRepository,
                        RecurrenceRuleRepository recurrenceRuleRepository,
                        RecurrenceService recurrenceService,
                        AttendeeRepository attendeeRepository) {
        this.eventRepository = eventRepository;
        this.recurrenceRuleRepository = recurrenceRuleRepository;
        this.recurrenceService = recurrenceService;
        this.attendeeRepository = attendeeRepository;
    }

    @Transactional
    public EventResponse create(EventRequest request) {
        Event event = new Event();
        apply(event, request);
        event.setCreatedAt(Instant.now());
        event.setUpdatedAt(event.getCreatedAt());
        Event saved = eventRepository.save(event);
        saveRecurrence(saved, request);
        saveAttendees(saved, request);
        return EventResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<EventResponse> list() {
        return eventRepository.findAll().stream().map(EventResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public EventResponse get(Long id) {
        return EventResponse.from(find(id));
    }

    @Transactional
    public EventResponse update(Long id, EventRequest request) {
        Event event = find(id);
        apply(event, request);
        event.setUpdatedAt(Instant.now());
        Event saved = eventRepository.save(event);
        saveRecurrence(saved, request);
        return EventResponse.from(saved);
    }

    @Transactional
    public void delete(Long id) {
        eventRepository.delete(find(id));
    }

    @Transactional(readOnly = true)
    public List<Occurrence> occurrences(Long id, Instant from, Instant to) {
        Event event = find(id);
        RecurrenceRule rule = recurrenceRuleRepository.findByEventId(id).orElse(null);
        if (rule == null) {
            return List.of();
        }
        return recurrenceService.materialize(event, rule, from, to);
    }

    private void saveRecurrence(Event event, EventRequest request) {
        recurrenceRuleRepository.findByEventId(event.getId()).ifPresent(recurrenceRuleRepository::delete);
        if (request.recurrence() != null) {
            RecurrenceRule rule = new RecurrenceRule();
            rule.setEvent(event);
            rule.setFrequency(request.recurrence().frequency());
            rule.setInterval(request.recurrence().interval() == null ? 1 : request.recurrence().interval());
            rule.setUntil(request.recurrence().until());
            recurrenceRuleRepository.save(rule);
        }
    }

    private void saveAttendees(Event event, EventRequest request) {
        if (request.attendees() == null) {
            return;
        }
        for (EventRequest.AttendeeRequest ar : request.attendees()) {
            Attendee attendee = new Attendee();
            attendee.setEvent(event);
            attendee.setEmail(ar.email());
            attendee.setStatus(ar.status() == null ? Status.PENDING : ar.status());
            attendeeRepository.save(attendee);
        }
    }

    private Event find(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    private void apply(Event event, EventRequest request) {
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setLocation(request.location());
        event.setStartTime(request.startTime());
        event.setEndTime(request.endTime());
    }
}