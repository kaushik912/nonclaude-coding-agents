package com.example.calendar.recurrence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurrenceRuleRepository extends JpaRepository<RecurrenceRule, Long> {
    Optional<RecurrenceRule> findByEventId(Long eventId);
}