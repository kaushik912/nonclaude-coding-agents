package com.example.calendar.event;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.startTime < :end AND e.endTime > :start ORDER BY e.startTime")
    List<Event> findOverlapping(@Param("start") Instant start, @Param("end") Instant end);
}