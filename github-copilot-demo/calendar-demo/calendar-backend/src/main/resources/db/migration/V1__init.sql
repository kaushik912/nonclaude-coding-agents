-- Calendar backend schema

CREATE TABLE events (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    location    VARCHAR(255),
    start_time  DATETIME(6) NOT NULL,
    end_time    DATETIME(6) NOT NULL,
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE recurrence_rules (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id  BIGINT NOT NULL,
    frequency VARCHAR(20) NOT NULL,          -- DAILY | WEEKLY | MONTHLY
    recur_interval INT NOT NULL DEFAULT 1,
    until     DATETIME(6),
    CONSTRAINT fk_recurrence_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE attendees (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id  BIGINT NOT NULL,
    email     VARCHAR(255) NOT NULL,
    status    VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- ACCEPTED | DECLINED | TENTATIVE | PENDING
    CONSTRAINT fk_attendee_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE reminders (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id       BIGINT NOT NULL,
    method         VARCHAR(20) NOT NULL,      -- EMAIL | POPUP
    minutes_before INT NOT NULL,
    CONSTRAINT fk_reminder_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_events_start_end ON events (start_time, end_time);
CREATE INDEX idx_attendees_event ON attendees (event_id);
CREATE INDEX idx_reminders_event ON reminders (event_id);
CREATE INDEX idx_recurrence_event ON recurrence_rules (event_id);