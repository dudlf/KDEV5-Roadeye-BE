package org.re.hq.driving.dto;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.re.hq.driving.domain.DrivingLocationLog;
import org.re.hq.reservation.domain.CarReservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DrivingLocationLogCreationCommand(
    @NonNull
    CarReservation carReservation,
    @NonNull
    BigDecimal latitude,
    @NonNull
    BigDecimal longitude,
    int carSpeed,
    @Nullable
    DrivingLocationLog prev,
    @NonNull
    LocalDateTime historyTime
) {
}
