package org.re.hq.reservation.api.payload;

import org.re.hq.reservation.domain.CarReservation;
import org.re.hq.reservation.domain.ReserveReason;
import org.re.hq.reservation.domain.ReserveStatus;

import java.time.LocalDateTime;

public record MyReservationResponse(
    Long carId,
    String carName,
    String licenseNumber,
    LocalDateTime rentStartAt,
    LocalDateTime rentEndAt,
    ReserveStatus reserveStatus,
    ReserveReason reserveReason,
    String rejectReason
) {

    public static MyReservationResponse from(CarReservation carReservation) {
        return new MyReservationResponse(
            carReservation.getId(),
            carReservation.getCar().getProfile().getName(),
            carReservation.getCar().getProfile().getLicenseNumber(),
            carReservation.getReservationPeriod().getRentStartAt(),
            carReservation.getReservationPeriod().getRentEndAt(),
            carReservation.getReserveStatus(),
            carReservation.getReserveReason(),
            carReservation.getReserveStatus().isRejected() ? carReservation.getRejectReason() : null
        );
    }
}
