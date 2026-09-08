package com.example.calendar.reminder;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByEventId(Long eventId);
}
