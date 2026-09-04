package com.example.calendar.recurrence;

import com.example.calendar.event.Event;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RecurrenceService {

    public List<Occurrence> materialize(Event event, RecurrenceRule rule, Instant from, Instant to) {
        List<Occurrence> occurrences = new ArrayList<>();
        Instant start = event.getStartTime();
        long durationMillis = event.getEndTime().toEpochMilli() - start.toEpochMilli();

        // Advance start to the window if needed
        while (start.isBefore(from)) {
            start = next(start, rule);
        }

        int guard = 0;
        while (!start.isAfter(to) && guard < 10000) {
            Instant end = start.plusMillis(durationMillis);
            if (!end.isBefore(from) && !start.isAfter(to)) {
                occurrences.add(new Occurrence(start, end));
            }
            start = next(start, rule);
            guard++;
        }
        return occurrences;
    }

    private Instant next(Instant current, RecurrenceRule rule) {
        return switch (rule.getFrequency()) {
            case DAILY -> current.plus(rule.getInterval(), ChronoUnit.DAYS);
            case WEEKLY -> current.plus(rule.getInterval(), ChronoUnit.WEEKS);
            case MONTHLY -> current.plus(rule.getInterval(), ChronoUnit.MONTHS);
        };
    }

    public record Occurrence(Instant startTime, Instant endTime) {
    }
}