package org.re.hq.driving.dto;

import org.re.hq.driving.domain.DrivingLocationLog;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public record DrivingLocationLogResponse(
    Long seq,
    Long reservationId,
    BigDecimal latitude,
    BigDecimal longitude,
    Duration driveTime,
    int carSpeed,
    LocalDateTime historyTime
) {
    public static DrivingLocationLogResponse from(DrivingLocationLog log) {
        return new DrivingLocationLogResponse(
            log.getId().getSequence(),
            log.getId().getCarReservation().getId(),
            log.getLocation().getLatitude(),
            log.getLocation().getLongitude(),
            log.getDriveTime(),
            log.getCarSpeed(),
            log.getHistoryTime()
        );
    }
}
