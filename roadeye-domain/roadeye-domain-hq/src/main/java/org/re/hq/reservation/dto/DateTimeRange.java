package org.re.hq.reservation.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public record DateTimeRange(LocalDateTime start, LocalDateTime end) {
    public DateTimeRange {
        if (Objects.isNull(start) || Objects.isNull(end)) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}
